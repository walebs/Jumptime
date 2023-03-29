package com.example.extremesport.view

data class ESUiState(
    val sunrise: Sunrise = Sunrise(),
    val nowcast: Nowcast = Nowcast(),
    val locationforecast = Locationforecast = Locationforecast(),
    val fellesDatakatalog = FellesDatakatalog = FellesDatakatalog()
)