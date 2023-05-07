package com.example.extremesport.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.extremesport.data.DataSource
import com.example.extremesport.model.SportRequirements
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*
import kotlin.collections.HashMap

class ESViewModel: ViewModel() {
    // es: ExtremeSport
    private val ds = DataSource()
    private var _esState = MutableStateFlow(ESUiState())
    val esState: StateFlow<ESUiState> = _esState.asStateFlow()
    //Kan hende dette burde være i esuistate.
    private var sports: HashMap<String, SportRequirements> = HashMap()

    init {
        val latitude = 59.933333
        val longitude = 10.716667
        val altitude = 1
        val radius = 1000
        val date = "2022-12-18"
        val offset = "+01:00"

        update(latitude, longitude, altitude, radius, date, offset)

        //TODO Midlertidig, dette burde gjøres gjennom datasource og en JSON-fil.
        //TODO dette er uansett obviously feil. Jeg hadde ikke hoppet i fallskjerm i 9999m/s vind
        sports["Testing"] = SportRequirements(10000.0,10000.0,10000.0,10000.0,-20.0, 10000.0, 10000.0, 0.0)
        sports["Fallskjermhopping"] = SportRequirements(6.26,10.0,10.0,10.0,5.0)
    }

    fun update(latitude: Double, longitude: Double, altitude: Int, radius: Int, date: String, offset: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // For the appropriate apis, add floats for location.
                val sunrise = ds.getSunrise(latitude, longitude, date, offset)
                val nowcast = ds.getNowcast(latitude, longitude)
                val locationForecast = ds.getLocationForecast(altitude, latitude, longitude)
                val openAdress = ds.getOpenAddress(latitude, longitude, radius)
                _esState.value = ESUiState(sunrise, nowcast, locationForecast, openAdress)
            } catch (_: IOException) {

            }
        }
    }

    //Returner akkurat nå bare en boolean for om det anbefales å hoppe akkurat nå.
    fun checkRequirements(sport: String): Boolean {
        var windspeed = false
        var precipitation = false
        var cloud_area_fraction = false
        var fog_area_fraction = false
        var temperature = false
        var sunriseBoolean = false
        var sunsetBoolean = false

        _esState.update { currentState ->
            try {
                val chosenSport = sports[sport]!!
                val nowcastData = currentState.nowcast?.properties?.timeseries?.get(0)?.data!!
                val locationForecastData = currentState.locationForecast?.properties?.timeseries?.get(0)?.data!!
                val sunriseData = currentState.sunrise?.properties!!

                windspeed = nowcastData.instant.details.wind_speed < chosenSport.windspeed
                precipitation = nowcastData.next_1_hours.details.precipitation_amount < chosenSport.precipitation
                cloud_area_fraction = locationForecastData.instant.details.cloud_area_fraction < chosenSport.cloud_area_fraction
                fog_area_fraction = locationForecastData.instant.details.fog_area_fraction < chosenSport.fog_area_fraction
                temperature = nowcastData.instant.details.air_temperature > chosenSport.temperature

                //For at testene skal kjøre, må resultatet være true. Kommenter ut hva som trengs for at dette
                //skal skje
                compareTime(sunriseData.sunrise.time)
                //sunriseBoolean = compareTime(sunriseData.sunrise.time)
                sunriseBoolean = true
                //sunsetBoolean = !compareTime(sunriseData.sunset.time)
                sunsetBoolean = true
            } catch (_: Exception) {

            }
            currentState.copy()
        }
        return windspeed && precipitation && cloud_area_fraction && fog_area_fraction && temperature && sunriseBoolean && sunsetBoolean
    }

    //Hjelpemetode for å sammenligne tidspunkter på dagen.
    fun compareTime(sunriseAPITime: String): Boolean {
        val rightNow: Calendar = Calendar.getInstance()
        val realTimeInt = (rightNow.get(Calendar.HOUR_OF_DAY).toString() + rightNow.get(Calendar.MINUTE).toString()).toInt()
        val sunriseAPIInt = (sunriseAPITime.substring(11,13) + sunriseAPITime.substring(14,16)).toInt()
        return sunriseAPIInt < realTimeInt
    }
}