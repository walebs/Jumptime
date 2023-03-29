package com.example.extremesport.model

data class LocationForecastData(
    val type: String
) {
    data class Geometry(
        val type: String,
        val coordinates: Array<Double>
    )
}