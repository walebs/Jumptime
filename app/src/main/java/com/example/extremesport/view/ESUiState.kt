package com.example.extremesport.view

import com.example.extremesport.model.LocationForecastData
import com.example.extremesport.model.NowcastData
import com.example.extremesport.model.OpenAddressData
import com.example.extremesport.model.SunriseData

data class ESUiState(
    val sunrise: SunriseData? = null,
    val nowcast: NowcastData? = null,
    val locationForecast: LocationForecastData? = null,
    val openAdress: OpenAddressData? = null
)