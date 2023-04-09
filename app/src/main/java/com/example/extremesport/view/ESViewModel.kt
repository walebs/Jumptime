package com.example.extremesport.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.extremesport.data.DataSource
import com.example.extremesport.model.LocationForecastData
import com.example.extremesport.model.NowcastData
import com.example.extremesport.model.SportRequirements
import com.example.extremesport.model.SunriseData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

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
        sports["Fallskjermhopping"] = SportRequirements(6.26,0.0,0.0,0.0,0.0)
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
                //TODO fjern
                println("Ferdig med å hente api-er")
            } catch (e: IOException) {

            }
        }
    }

    //Returner akkurat nå bare en boolean for om det anbefales å hoppe akkurat nå.
    fun checkRequirements(sport: String, nowcast: NowcastData, locationForecast: LocationForecastData, sunrise: SunriseData): Boolean {
        val windspeed: Boolean
        val precipitation: Boolean
        val cloud_area_fraction: Boolean
        val fog_area_fraction: Boolean
        val temperature: Boolean
        val sunriseBoolean: Boolean
        val sunsetBoolean: Boolean

        _esState.update { currentState ->
            println((currentState.nowcast?.type ?: "Feil"))
            currentState.copy()
        }

        try {
            val chosenSport = sports[sport]!!
            val nowcastData = nowcast.properties.timeseries[0].data
            val locationForecastData = locationForecast.properties.timeseries[0].data
            val sunriseData = sunrise.properties

            windspeed = nowcastData.instant.details.wind_speed < chosenSport.windspeed
            precipitation = nowcastData.next_1_hours.details.precipitation_amount < chosenSport.precipitation
            cloud_area_fraction = locationForecastData.instant.details.cloud_area_fraction < chosenSport.cloud_area_fraction
            fog_area_fraction = locationForecastData.instant.details.fog_area_fraction < chosenSport.fog_area_fraction
            temperature = nowcastData.instant.details.air_temperature > chosenSport.temperature
            //TODO fjern
            val currentTime = "Todo"
            println(currentTime)
            println("\n\n")
            sunriseBoolean = true
            sunsetBoolean = true
        } catch (e: Exception) {
            return false
        }
        return windspeed && precipitation && cloud_area_fraction && fog_area_fraction && temperature && sunriseBoolean && sunsetBoolean
    }
}