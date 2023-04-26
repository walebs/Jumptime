package com.example.extremesport.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.extremesport.Screens
import com.example.extremesport.view.ESViewModel

@Composable
fun LoadingScreen(navController: NavController, loadingFunction: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(Color.Red)) {
    }
    loadingFunction()
    navController.navigate(Screens.MainScreen.name) {popUpTo(Screens.LoadingScreen.name)}
}