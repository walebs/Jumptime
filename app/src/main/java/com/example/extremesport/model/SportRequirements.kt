package com.example.extremesport.model

data class SportRequirements(
    val windspeed: Double,
    val precipitation: Double,
    //Hvor tett skyene er i akkurat det området.
    val cloud_area_fraction: Double,
    //Hvor tett tåka er i akkurat det området.
    val fog_area_fraction: Double
)