package com.example.extremesport

import com.example.extremesport.data.DataSource
import com.example.extremesport.view.ESViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class CheckRequirementsTest {
    @Test
    fun parachuteRequirements_isCorrect() {
<<<<<<< HEAD
        val viewmodel = ESViewModel(null)

        //TODO venter på at api-ene skal loade, og dette er antakeligvis ikke den beste løsningen lol.
=======
        val viewmodel = ESViewModel()
>>>>>>> origin/main
        Thread.sleep(5000)

        val cR = viewmodel.checkRequirements("Testing")
        Assert.assertEquals(true, cR > 0)
    }

    @Test
    fun compareTimeTest() {
        val vm = ESViewModel()
        Thread.sleep(5000)

        Assert.assertEquals(true, vm.compareTime("2022-12-18T00:00+01:00"))
        //"2022-12-18T09:16+01:00"
    }
}