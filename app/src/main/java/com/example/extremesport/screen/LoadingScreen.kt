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
fun LoadingScreen(vm: ESViewModel, loadFunction: (ESViewModel) -> Unit, onNavigateToNext: () -> Unit){
    Column {
        Spacer(
            modifier = Modifier.fillMaxSize()
                .background(Color(android.graphics.Color.parseColor("#ff0000")))
        )
        loadFunction(vm)
        onNavigateToNext()
    }
}