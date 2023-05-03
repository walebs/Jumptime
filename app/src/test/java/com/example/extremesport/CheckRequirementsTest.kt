package com.example.extremesport

import com.example.extremesport.view.ESViewModel
import org.junit.Assert
import org.junit.Test

class CheckRequirementsTest {
    @Test
    fun parachuteRequirements_isCorrect() {
        val viewmodel = ESViewModel(null)

        //TODO venter på at api-ene skal loade, og dette er antakeligvis ikke den beste løsningen lol.
        Thread.sleep(5000)

        val cRBoolean = viewmodel.checkRequirements("Testing")
        Assert.assertEquals(true, cRBoolean)
    }
}