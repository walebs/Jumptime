package com.example.extremesport.model

data class LocationForecastData(
    val geometry: Geometry,
    val properties: Properties,
    val type: String
) {
    data class Geometry(
        val coordinates: Array<Double>,
        val type: String
    )

    data class Properties(
        val meta: Meta,
        val timeseries: Array<Dataholder>
    )

    data class Meta(
        val units: Units,
        val updated_at: String
    )

    data class Units(
        val air_pressure_at_sea_level: String,
        val air_temperature: String,
        val air_temperature_max: String,
        val air_temperature_min: String,
        val cloud_area_fraction: String,
        val cloud_area_fraction_high: String,
        val cloud_area_fraction_low: String,
        val cloud_area_fraction_medium: String,
        val dew_point_temperature: String,
        val fog_area_fraction: String,
        val precipitation_amount: String,
        val precipitation_amount_max: String,
        val precipitation_amount_min: String,
        val probability_of_precipitation: String,
        val probability_of_thunder: String,
        val relative_humidity: String,
        val ultraviolet_index_clear_sky_max: String,
        val wind_from_direction: String,
        val wind_speed: String,
        val wind_speed_of_gust: String,
    )

    data class Dataholder(
        val data: Data,
        val time: String
    )

    data class Data(
        val instant: String,
        val next_12_hours: String,
        val next_1_hours: String,
        val next_6_hours: String,
    )
}