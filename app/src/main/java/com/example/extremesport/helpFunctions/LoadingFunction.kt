package com.example.extremesport.helpFunctions

import com.example.extremesport.view.ESUiState

fun loadAPIs(state: ESUiState) {

    while(state.nowcast == null) {
        Thread.sleep(1)
    }

    while(state.sunrise == null) {
        Thread.sleep(1)
    }

    while(state.locationForecast == null) {
        Thread.sleep(1)
    }

    while(state.openAdress == null) {
        Thread.sleep(1)
    }
}