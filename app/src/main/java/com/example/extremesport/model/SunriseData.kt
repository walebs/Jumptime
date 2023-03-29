package com.example.extremesport.model

data class SunriseData(
    val copyright: String,
    val licenseURL: String,
    val type: String,
    val geometry: Geometry,
    val `when`: When,
    val properties: Properties
    )

data class Geometry(
    val type: String,
    val coordinates: Array<Double>
)

data class When(
    val interval: Array<String>
)

data class Properties(
    val body: String,
    val sunrise : Sunrise,
    val sunset: Sunset,
    val solarnoon: Solarnoon,
    val solarmidnight: Solarmidnight
)

data class Sunrise(
    val time: String,
    val azimuth: Double
)

data class Sunset(
    val time: String,
    val azimuth: Double
)

data class Solarnoon(
    val time: String,
    val disc_centre_elevation: Double,
    val visible: Boolean
)

data class Solarmidnight(
    val time: String,
    val disc_centre_elevation: Double,
    val visible: Boolean
)