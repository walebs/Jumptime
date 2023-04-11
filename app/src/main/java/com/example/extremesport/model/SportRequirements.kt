package com.example.extremesport.model

/*Det er flere krav, men ingen som er hardkodet egentlig. F.eks, når det er soloppgang og
solnedgang er viktig, men dette er bare noe vi må sjekke for i funksjonen.
 */
data class SportRequirements(
    val windspeed: Double,
    val precipitation: Double,
    //Hvor tett skyene er i akkurat det området.
    val cloud_area_fraction: Double,
    //Hvor tett tåka er i akkurat det området.
    val fog_area_fraction: Double,
    val temperature: Double
)