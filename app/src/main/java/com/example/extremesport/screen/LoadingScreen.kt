package com.example.extremesport.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.extremesport.R
import com.example.extremesport.Screens
import com.example.extremesport.view.ESViewModel

@Composable
fun LoadingScreen(navController: NavController, loadingFunction: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.clock_icon),
            contentDescription = null,
            Modifier
                .size(100.dp)
                .wrapContentSize()
        )
    }
    loadingFunction()
    navController.navigate(Screens.MainScreen.name) {popUpTo(Screens.LoadingScreen.name)}
}

fun loadAPIs(vm: ESViewModel) {

    while(vm.esState.value.nowcast == null
        || vm.esState.value.sunrise == null
        || vm.esState.value.locationForecast == null
        || vm.esState.value.openAdress == null) {
        Thread.sleep(1)
    }
}