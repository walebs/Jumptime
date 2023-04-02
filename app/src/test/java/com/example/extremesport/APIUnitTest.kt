package com.example.extremesport

import com.example.extremesport.data.DataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class APIUnitTest {
    @Test
    fun sunrise_isCorrect() {
        val dataSource = DataSource()
        val sunrisetest = runBlocking { dataSource.getSunrise(59.933333,10.716667,"2022-12-18","+01:00") }
        Assert.assertEquals("MET Norway", sunrisetest.copyright)
        Assert.assertEquals("Feature", sunrisetest.type)
        Assert.assertEquals("Point", sunrisetest.geometry.type)
    }
    @Test
    fun locationForecast_isCorrect() {
        val dataSource = DataSource()
        val locationForecast = runBlocking { dataSource.getLocationForecast() }
        Assert.assertEquals("Feature", locationForecast.type)
        Assert.assertEquals("Point", locationForecast.geometry.type)
    }
}