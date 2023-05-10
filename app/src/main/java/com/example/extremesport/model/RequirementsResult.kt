package com.example.extremesport.model

data class RequirementsResult(
    var summaryCode1: String,
    var summaryCode6: String,
    var summaryCode12:String,
    var currentTemp: Double,
    var highTemp1: Double,
    var lowTemp1: Double,
    var highTemp6: Double,
    var lowTemp6: Double,
    var highTemp12: Double,
    var lowTemp12: Double,
    var windStrength: Double,
    var windDirection: Double,
    var openAddressName: String
)