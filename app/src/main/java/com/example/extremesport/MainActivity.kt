package com.example.extremesport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Applier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.extremesport.screen.MainScreen
import com.example.extremesport.screen.ProfileScreen
import com.example.extremesport.screen.ScoreBoardScreen
import com.example.extremesport.screen.SettingsScreen
import com.example.extremesport.ui.theme.ExtremeSportTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExtremeSportTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main" ){
        composable("main") { MainScreen( onNavigateToNext = { navController.navigate("settings")}) }
        composable("settings") { SettingsScreen () }
        composable("profile") { ProfileScreen () }
        composable("score") { ScoreBoardScreen () }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ExtremeSportTheme {
       App()
    }
}

// Revrt commit