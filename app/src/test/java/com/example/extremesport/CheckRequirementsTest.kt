package com.example.extremesport

import com.example.extremesport.view.ESViewModel
import org.junit.Assert
import org.junit.Test

class CheckRequirementsTest {
    @Test
    fun parachuteRequirements_isCorrect() {
        val viewmodel = ESViewModel()

        //TODO venter på at api-ene skal loade, og dette er antakeligvis ikke den beste løsningen lol.
        Thread.sleep(5000)

        val cR = viewmodel.checkRequirements("Testing")
        Assert.assertEquals(true, cR.numbAverage > 0)
    }
}