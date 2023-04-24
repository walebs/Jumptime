package com.example.extremesport

import com.example.extremesport.helpFunctions.loadAPIs
import com.example.extremesport.view.ESViewModel
import org.junit.Assert
import org.junit.Test

class LoadingUnitTest {
    @Test
    fun loading_isCorrect() {
        val viewmodel = ESViewModel()
        loadAPIs(viewmodel)

        Assert.assertEquals("Feature", viewmodel.esState.value.sunrise!!.type)
        Assert.assertEquals("Point", viewmodel.esState.value.sunrise!!.geometry.type)

    }
}