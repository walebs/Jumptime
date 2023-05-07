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
        val date = "2023-05-07"
        val offset = "+01:00"

        update(latitude, longitude, altitude, radius, date, offset)

        //TODO Midlertidig, dette burde gjøres gjennom datasource og en JSON-fil.
        //TODO numrene må fininnstilles.
        sports["Testing"] = SportRequirements(
            10000.0, 10000.0,
            10000.0, 10000.0,
            10000.0, 10000.0,
            listOf(-20.0, 10000.0), listOf(-20.0, 10000.0),
            10000.0, 10000.0,
            10000.0, 10000.0,
            10000.0, 10000.0,
            true
        )
        sports["Fallskjermhopping"] = SportRequirements(
            6.0, 10.0,
            0.0, 0.1,
            5.0, 5.0,
            listOf(10.0, 35.0), listOf(5.0, 35.0),
            0.0, 10.0,
            3.0, 6.0,
            4.0, 7.0,
            false
        )
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
        var reqTest: Double

        _esState.update { currentState ->
            try {
                val chosenSport = sports[sport]!!
                val nowcastData = currentState.nowcast?.properties?.timeseries?.get(0)?.data!!
                val locationForecastData = currentState.locationForecast?.properties?.timeseries?.get(0)?.data!!
                val sunriseData = currentState.sunrise?.properties!!

                val windspeed: Int
                val wind_speed_of_gust: Int
                val temp: Int
                val precipitation: Int
                val probability_of_thunder: Int
                val uv_index: Int

                windspeed = if(nowcastData.instant.details.wind_speed <= chosenSport.windspeed_ideal) {
                    2
                } else if(nowcastData.instant.details.wind_speed <= chosenSport.windspeed_moderate) {
                    1
                } else {
                    0
                }

                wind_speed_of_gust = if(nowcastData.instant.details.wind_speed_of_gust <= chosenSport.wind_speed_of_gust_ideal) {
                    2
                } else if (nowcastData.instant.details.wind_speed_of_gust <= chosenSport.wind_speed_of_gust_moderate) {
                    1
                } else {
                    0
                }

                temp = if(nowcastData.instant.details.air_temperature <= chosenSport.temperature_ideal[1] && nowcastData.instant.details.air_temperature >= chosenSport.temperature_ideal[0]) {
                    2
                } else if (nowcastData.instant.details.air_temperature <= chosenSport.temperature_moderate[1] && nowcastData.instant.details.air_temperature >= chosenSport.temperature_moderate[0]) {
                    1
                } else {
                    0
                }

                precipitation = if(locationForecastData.next_1_hours.details.precipitation_amount <= chosenSport.precipitation_ideal) {
                    2
                } else if(locationForecastData.next_1_hours.details.precipitation_amount <= chosenSport.precipitation_moderate) {
                    1
                } else {
                    0
                }

                probability_of_thunder = if(locationForecastData.next_1_hours.details.probability_of_thunder <= chosenSport.probability_of_thunder_ideal) {
                    2
                } else if(locationForecastData.next_1_hours.details.probability_of_thunder <= chosenSport.probability_of_thunder_moderate) {
                    1
                } else {
                    0
                }

                uv_index = if(locationForecastData.next_1_hours.details.ultraviolet_index_clear_sky_max > chosenSport.uv_index_ideal) {
                    2
                } else if(locationForecastData.next_1_hours.details.ultraviolet_index_clear_sky_max > chosenSport.uv_index_moderate) {
                    1
                } else {
                    0
                }

                val cloud_area: Boolean = locationForecastData.instant.details.cloud_area_fraction < chosenSport.cloud_area_fraction
                val fog_area: Boolean = locationForecastData.instant.details.fog_area_fraction < chosenSport.fog_area_fraction
                val sunriseBoolean = compareTime(sunriseData.sunrise.time)
                val sunsetBoolean = !compareTime(sunriseData.sunset.time)

                val boolcollector = cloud_area && fog_area && ((sunriseBoolean && sunsetBoolean) || chosenSport.test)
                val numbaverage = if(windspeed == 0 || wind_speed_of_gust == 0 || temp == 0 || precipitation == 0 || probability_of_thunder == 0 || uv_index == 0) {
                    0
                } else {
                    (windspeed + wind_speed_of_gust + temp + precipitation + probability_of_thunder + uv_index) / 6
                }
            } catch (_: Exception) {

            }
            currentState.copy()
        }
        return true
    }

    //Hjelpemetode for å sammenligne tidspunkter på dagen.
    fun compareTime(sunriseAPITime: String): Boolean {
        val rightNow: Calendar = Calendar.getInstance()
        val realTimeInt = (rightNow.get(Calendar.HOUR_OF_DAY).toString() + rightNow.get(Calendar.MINUTE).toString()).toInt()
        val sunriseAPIInt = (sunriseAPITime.substring(11,13) + sunriseAPITime.substring(14,16)).toInt()
        return sunriseAPIInt < realTimeInt
    }
}