package com.example.extremesport.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.extremesport.data.DataSource
import com.example.extremesport.model.SportRequirements
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
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
        val radius = 0
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
            } catch (e: IOException) {

            }
        }
    }

    //Returner akkurat nå bare en boolean for om det anbefales å hoppe akkurat nå.
    //@Composable
    fun checkRequirements(sport: String): Boolean {
        val windspeed: Boolean
        val precipitation: Boolean
        val cloud_area_fraction: Boolean
        val fog_area_fraction: Boolean
        val temperature: Boolean
        val sunrise: Boolean
        val sunset: Boolean

        try {
            //val uiStateValue by _esState.collectAsState()
            val chosenSport = sports[sport]!!
            val nowcastData = _esState.value.nowcast?.properties?.timeseries?.get(0)?.data!!
            //TODO fjern
            println("fant nowcast")
            val locationForecastData = _esState.value.locationForecast?.properties?.timeseries?.get(0)?.data!!
            //TODO fjern
            println("fant lf")
            val sunriseData = _esState.value.sunrise?.properties!!
            //TODO fjern
            println("fant sr")

            windspeed = nowcastData.instant.details.wind_speed < chosenSport.windspeed
            precipitation = nowcastData.next_1_hours.details.precipitation_amount < chosenSport.precipitation
            cloud_area_fraction = locationForecastData.instant.details.cloud_area_fraction < chosenSport.cloud_area_fraction
            fog_area_fraction = locationForecastData.instant.details.fog_area_fraction < chosenSport.fog_area_fraction
            temperature = nowcastData.instant.details.air_temperature > chosenSport.temperature
            //TODO fjern
            val currentTime = System.currentTimeMillis()
            println(currentTime)
            println("\n\n\n\n\n\n\n")
            sunrise = true
            sunset = true
        } catch (e: Exception) {
            //TODO fjern
            println("Feilet \n\n\n\n")
            return false
        }

        return windspeed && precipitation && cloud_area_fraction && fog_area_fraction && temperature && sunrise && sunset
    }
}