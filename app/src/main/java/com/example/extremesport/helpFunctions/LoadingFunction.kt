package com.example.extremesport.helpFunctions

import com.example.extremesport.view.ESUiState
import com.example.extremesport.view.ESViewModel

fun loadAPIs(vm: ESViewModel) {

    while(vm.esState.value.nowcast == null) {
        Thread.sleep(1)
    }

    while(vm.esState.value.sunrise == null) {
        Thread.sleep(1)
    }

    while(vm.esState.value.locationForecast == null) {
        Thread.sleep(1)
    }

    while(vm.esState.value.openAdress == null) {
        Thread.sleep(1)
    }
}