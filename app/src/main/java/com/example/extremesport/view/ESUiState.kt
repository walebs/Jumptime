package com.example.extremesport.view

data class ESUiState(
    val sunrise: SunriseData?,
    val nowcast: NowcastData?,
    val locationforecast = LocationforecastData?,
    val fellesDatakatalog = FellesDatakatalogData?
)