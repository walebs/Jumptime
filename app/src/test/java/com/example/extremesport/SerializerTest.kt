package com.example.extremesport

import com.example.extremesport.data.DataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class SerializerTest {
    @Test
    fun locationData_isCorrect() {
        val dataSource = DataSource()
        val locationDataTest = runBlocking{ dataSource.getLocationData() }
        Assert.assertEquals(true, locationDataTest.locations.isNotEmpty())
    }
}