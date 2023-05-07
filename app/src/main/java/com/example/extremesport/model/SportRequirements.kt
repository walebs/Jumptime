package com.example.extremesport.model

/*Det er flere krav, men ingen som er hardkodet egentlig. F.eks, når det er soloppgang og
solnedgang er viktig, men dette er bare noe vi må sjekke for i funksjonen.
 */
data class SportRequirements(
    val windspeed_ideal: Double,
    val windspeed_moderate: Double,
    val windspeed_bad: Double,
    val precipitation_ideal: Double,
    val precipitation_moderate: Double,
    val precipitation_bad: Double,
    //Hvor tett skyene er i akkurat det området.
    val cloud_area_fraction: Double,
    //Hvor tett tåka er i akkurat det området.
    val fog_area_fraction: Double,
    val temperature_ideal: Double,
    val temperature_moderate: Double,
    val temperature_bad: Double,
    val probability_of_thunder_ideal: Double,
    val probability_of_thunder_moderate: Double,
    val probability_of_thunder_bad: Double,
    val wind_speed_of_gust_ideal: Double,
    val wind_speed_of_gust_moderate: Double,
    val wind_speed_of_gust_bad: Double,
    val uv_index_ideal: Double,
    val uv_index_moderate: Double,
    val uv_index_bad: Double,
)