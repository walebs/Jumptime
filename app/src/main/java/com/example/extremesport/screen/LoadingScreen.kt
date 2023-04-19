package com.example.extremesport.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.extremesport.helpFunctions.loadAPIs
import com.example.extremesport.view.ESViewModel


@Composable
fun LoadingScreen(loadingFunction: () -> Unit, onNavigateToNext: () -> Unit){
    /* TODO
        Currently is only a red screen, maybe this should be the logo or something?
     */
    Column {
        Spacer(
            modifier = Modifier.fillMaxSize()
                .background(Color(android.graphics.Color.parseColor("#ff0000")))
        )
    }
    loadingFunction()
    onNavigateToNext()
}