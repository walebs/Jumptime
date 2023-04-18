package com.example.extremesport.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.extremesport.helpFunctions.loadAPIs
import com.example.extremesport.view.ESViewModel


@Composable
fun LoadingScreen(vm: ESViewModel, onNavigateToNext: () -> Unit){
    Column() {
        Spacer(
            modifier = Modifier.fillMaxWidth().height(25.dp)
                .background(Color(android.graphics.Color.parseColor("#ff0000")))
        )
        loadAPIs(vm)
        onNavigateToNext()
    }
}