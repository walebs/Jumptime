package com.example.extremesport.screen

import android.annotation.SuppressLint
import android.graphics.Color.parseColor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.extremesport.R
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen (onNavigateToNext: () -> Unit ) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                backgroundColor = "#DEDBE4".color
            )
            {
                Icon(imageVector = Icons.Default.Add, null)
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,

        drawerContent = { DrawerMenu(onNavigateToNext) },

        bottomBar = {
            BottomAppBar(
                cutoutShape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
                backgroundColor = "#296BA9".color
            )
            {
                BottomNavigationItem(
                    selected = false,
                    onClick = { coroutineScope.launch { scaffoldState.drawerState.open() } },
                    selectedContentColor = Color.Red,
                    unselectedContentColor = Color.White,
                    icon = {
                        Icon(painterResource(id = R.drawable.hamburger), null)
                    }
                )

                Spacer(modifier = Modifier.padding(70.dp))

                BottomNavigationItem(
                    selected = false,
                    onClick = onNavigateToNext,
                    selectedContentColor = Color.Red,
                    unselectedContentColor = Color.White,
                    icon = {
                        Icon(painterResource(id = R.drawable.settings2), null)
                    }
                )
            }
        }
    ) {
    }
}
@Composable
fun DrawerMenu(onNavigateToNext: () -> Unit ){
            Box(
                modifier = Modifier
                    .background(color = "#296BA9".color)
                    .size(400.dp, 300.dp)
            ) {
                Column {
                    Spacer(modifier = Modifier.padding(30.dp))
                    Row(
                        modifier = Modifier
                            .padding(start = 30.dp),
                    ) {
                        Icon(painterResource(id = R.drawable.profile2), null )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Text(fontWeight = FontWeight.Bold, text = "  Navn")
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(fontWeight = FontWeight.Bold, text = "  Poeng")
                }
            }

            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "Profil", fontWeight = FontWeight.Bold)
            }
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "Scoreboard", fontWeight = FontWeight.Bold)
            }
            TextButton(onClick = onNavigateToNext ) {
                Text(text = "Innstillinger", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.padding(100.dp))

            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "Om oss", fontWeight = FontWeight.Bold)
            }
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "Rapporter", fontWeight = FontWeight.Bold)
            }
}

val String.color get() = Color(parseColor(this))
