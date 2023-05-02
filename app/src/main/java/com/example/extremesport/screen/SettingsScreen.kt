package com.example.extremesport.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.extremesport.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingsScreen(navController: NavController) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
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
                .offset(y = -50.dp)
        ) {
            Image(
                painterResource(id = R.drawable.jumptime_tekst_whiteontransparent),
                contentDescription = "Logonavn",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .size(130.dp)
                    .offset(x = -17.dp),
            )
            Text(
                text = "Settings",
                fontSize = 30.sp,
                color = Color.White,
                modifier = Modifier
                        .offset(y = -55.dp)
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
                //Kan være vi bør gjøre den mindre m tanke på at vi har få settings
                .height(screenHeight - 120.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(id = R.drawable.baseline_mode_night_24_black),
                    contentDescription = "Logo for night mode")
                Spacer(modifier = Modifier.padding(3.dp))
                Text(
                    text = "Night Mode",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Test")
                }
            }
            Row(
                modifier = Modifier.padding(15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(id = R.drawable.baseline_mode_night_24_black),
                    contentDescription = "Ny setting")
                Spacer(modifier = Modifier.padding(3.dp))
                Text(
                    text = "Ny Setting",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Test")
                }
            }
        }
    }
}



