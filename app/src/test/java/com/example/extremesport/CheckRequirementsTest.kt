package com.example.extremesport

import com.example.extremesport.data.DataSource
import com.example.extremesport.view.ESViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class CheckRequirementsTest {
    @Test
    fun parachuteRequirements_isCorrect() {
        val viewmodel = ESViewModel()
        val dataSource = DataSource()

        Thread.sleep(2000)

        val nowcastTest = runBlocking { dataSource.getNowcast(59.933333, 10.716667) }
        val locationForecastTest = runBlocking { dataSource.getLocationForecast(1,59.911491,10.757933) }
        val sunriseTest = runBlocking { dataSource.getSunrise(59.933333,10.716667,"2022-12-18","+01:00") }

        val cRBoolean = viewmodel.checkRequirements("Fallskjermhopping", nowcastTest, locationForecastTest, sunriseTest)
        Assert.assertEquals(true, cRBoolean)
    }
}