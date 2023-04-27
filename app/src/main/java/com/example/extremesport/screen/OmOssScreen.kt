package com.example.extremesport.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.extremesport.R

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OmOssScreen(navController: NavController) {
    val skop = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
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
                Spacer(modifier = Modifier.padding(15.dp))
                Text(
                    text = "Om oss",
                    fontSize = 30.sp,
                    color = Color.White
                )
            }
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp)
                    .padding(20.dp)
                    .offset(y = 100.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painterResource(id = R.drawable.ifibygg),
                        contentDescription = "Bilde av hele ifi bygget",
                    )
                    Text(
                        text = "(Bilde over institutt for informatikk)",
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.padding(15.dp))
                   Text(
                       text = "Vi er utviklerene av applikasjonen JumpTime, og kommer fra en bakgrunn innen informatikk fra UiO. [LEGG TIL MER]"
                   )
                }
            }
        }
    }
}