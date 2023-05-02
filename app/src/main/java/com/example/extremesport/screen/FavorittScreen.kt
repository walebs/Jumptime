package com.example.extremesport.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun FavorittScreen(navController: NavController){
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .background(Color.LightGray, RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                .alpha(1f)
                .padding(5.dp)
                .align(Alignment.BottomCenter)
                .fillMaxSize()
        ) {}
        Column(
            modifier = Modifier
                .background("#1C6EAE".color)
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp)
        ) {
            Text(
                text = "LOGONAVN",
                color = Color.White
            )
            Text(
                text = "Favoritter - <3!",
                fontSize = 30.sp,
                color = Color.White
            )
        }
        Column(
            modifier = Modifier
                .background(
                    color = Color.White,
                    RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp)
                )
                .alpha(1f)
                .clip(shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                .padding(5.dp)
                .align(Alignment.BottomCenter)
                .height(screenHeight - 120.dp)
                .fillMaxWidth()
        ) {
            Column {
                LazyColumn {
                    //Dette må være antall stasjoner brukeren har hoppet
                    items(1) {
                        Cards(
                            stationName = "StasjonNavn",
                            rating = 3,
                            stationInfo = "Mer informasjon"
                        )
                    }
                }
            }
        }
    }
}