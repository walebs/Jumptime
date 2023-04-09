package com.example.extremesport

import com.example.extremesport.view.ESViewModel
import org.junit.Test

class CheckRequirementsTest {
    @Test
    fun parachuteRequirements_isCorrect() {
        val viewmodel = ESViewModel()
        viewmodel.checkRequirements("Fallskjermhopping")
    }
}