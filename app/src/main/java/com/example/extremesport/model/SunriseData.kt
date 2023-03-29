package com.example.extremesport.model

data class SunriseData(
    val copyright: String,
    val licenseURL: String,
    val type: String,
    val geometry: Geometry,
    val `when`: When,
    val properties: Properties
    ) {
}

data class Geometry(
    val type: String,
    val coordinates: Array<Double>
){
}

data class When(
    val interval: Array<String>
) {
}

data class Properties(
    val body: String,
    val sunrise : Sunrise,
    val sunset: Sunset,
    val solarnoon: Solarnoon,
    val solarmidnight: Solarmidnight
){
}

data class Sunrise(
    val time: String,
    val azimuth: Double
){
}

data class Sunset(
    val time: String,
    val azimuth: Double
) {
}

data class Solarnoon(
    val time: String,
    val disc_centre_elevation: Double,
    val visible: Boolean
) {
}

data class Solarmidnight(
    val time: String,
    val disc_centre_elevation: Double,
    val visible: Boolean
) {
}

/*
{
        "copyright": "MET Norway",
        "licenseURL": "https://api.met.no/license_data.html",
        "type": "Feature",
        "geometry": {
            "type": "Point",
            "coordinates": [
                10.7,
                59.9
            ]
    },
        "when": {
            "interval": [
                "2023-03-28T23:17:00Z",
                "2023-03-29T23:22:00Z"
            ]
    },
        "properties": {
            "body": "Sun",
            "sunrise": {
            "time": "2023-03-29T04:52+00:00",
            "azimuth": 82.03
        },
            "sunset": {
            "time": "2023-03-29T17:52+00:00",
            "azimuth": 278.4
        },
            "solarnoon": {
            "time": "2023-03-29T11:22+00:00",
            "disc_centre_elevation": 33.47,
            "visible": true
        },
            "solarmidnight": {
            "time": "2023-03-28T23:22+00:00",
            "disc_centre_elevation": -26.92,
            "visible": false
        }
    }
}
*/