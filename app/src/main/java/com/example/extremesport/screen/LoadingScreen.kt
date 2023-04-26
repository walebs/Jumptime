package com.example.extremesport.screen

import androidx.navigation.NavController
import com.example.extremesport.Screens
import com.example.extremesport.view.ESViewModel

fun LoadingScreen(
    navController: NavController,
    loadingFunction: (ESViewModel) -> Unit,
    viewModel: ESViewModel) {
    loadingFunction(viewModel)
    navController.navigate(Screens.MainScreen.name) {popUpTo(Screens.LoadingScreen.name)}
}