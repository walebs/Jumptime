package com.example.extremesport.screen

import android.annotation.SuppressLint
import android.graphics.Color.parseColor
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.extremesport.R
import com.example.extremesport.ui.theme.Pink40


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen (){
    Scaffold(
        bottomBar = {
            NavigationView()
        }
    ) {

    }
}


@Composable
fun NavigationView() {
    val items = listOf(
        R.drawable.hamburger,
        R.drawable.add_circle,
        R.drawable.settings
    )

    BottomNavigation(backgroundColor = "#296BA9".color) {
        BottomNavigationItem(
                selected = false,
                onClick = { },
                selectedContentColor = Color.Red,
                unselectedContentColor = Color.White,
                icon = {
                    Icon(painterResource(id = R.drawable.hamburger), null)
                }
            )

        BottomNavigationItem(
            selected = false,
            onClick = { },
            selectedContentColor = Color.Red,
            unselectedContentColor = Color.White,
            icon = {
                Icon(painterResource(id = R.drawable.add_circle), null)
            }
        )

        BottomNavigationItem(
            selected = false,
            onClick = { },
            selectedContentColor = Color.Red,
            unselectedContentColor = Color.White,
            icon = {
                Icon(painterResource(id = R.drawable.settings), null)
            }
        )
    }
}

val String.color get() = Color(parseColor(this))
