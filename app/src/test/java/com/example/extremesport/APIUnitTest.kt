package com.example.extremesport

import com.example.extremesport.data.DataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class APIUnitTest {
    @Test
    fun sunrise_isCorrect() {
        val dataSource = DataSource()
        val sunriseTest = runBlocking { dataSource.getSunrise(59.933333,10.716667,"2022-12-18","+01:00") }
        Assert.assertEquals("MET Norway", sunriseTest.copyright)
        Assert.assertEquals("Feature", sunriseTest.type)
        Assert.assertEquals("Point", sunriseTest.geometry.type)
    }

    @Test
    fun nowcast_isCorrect() {
        val dataSource = DataSource()
        val nowcastTest = runBlocking { dataSource.getNowcast(59.933333, 10.716667) }
        Assert.assertEquals("Feature", nowcastTest.type)
        Assert.assertEquals("Point", nowcastTest.geometry.type)
    }

    @Test
    fun locationForecast_isCorrect() {
        val dataSource = DataSource()
        val locationForecastTest = runBlocking { dataSource.getLocationForecast(1,59.911491,10.757933) }
        Assert.assertEquals("Feature", locationForecastTest.type)
        Assert.assertEquals("Point", locationForecastTest.geometry.type)
    }

    @Test
    fun openAddress_isCorrect() {
        val dataSource = DataSource()
        val openAddress = runBlocking { dataSource.getOpenAddress(59.911491, 10.757933, 1000) }
        Assert.assertEquals(0, openAddress.metadata.side)
        Assert.assertEquals(true, openAddress.metadata.asciiKompatibel)
    }
}