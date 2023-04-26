package com.example.extremesport.helpFunctions

import com.example.extremesport.view.ESViewModel

fun loadAPIs(vm: ESViewModel) {

    while(vm.esState.value.nowcast == null
        || vm.esState.value.sunrise == null
        || vm.esState.value.locationForecast == null
        || vm.esState.value.openAdress == null) {
        Thread.sleep(1)
    }
}