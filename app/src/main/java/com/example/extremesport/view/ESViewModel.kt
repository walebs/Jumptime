package com.example.extremesport.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class ESViewModel: ViewModel() {
    // es: ExtremeSport
    private val ds = DataSource()
    private var _esState = MutableStateFlow(ESUiState())
    val esState: StateFlow<ESUiState> = _esState.asStateFlow()

    init {
        update()
    }

    fun update() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // For the appropriate apis, add floats for location.
                val sunrise = ds.getSunrise()
                val nowcast = ds.getNowcast()
                val locationforecast = ds.getLocationforecast()
                val fellesDatakatalog = ds.getFellesDatakatalog()
                _esState.value = ESUiState(sunrise, nowcast, locationforecast, fellesDatakatalog)
            } catch (e: IOException) {

            }
        }
    }
}