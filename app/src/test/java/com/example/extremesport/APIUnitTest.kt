package com.example.extremesport

import com.example.extremesport.data.DataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class APIUnitTest {
    @Test
    fun sunrise_isCorrect() {
        val dataSource = DataSource()
        val sunrisetest = runBlocking { dataSource.getSunrise() }
        Assert.assertEquals("MET Norway", sunrisetest.copyright)
    }
}