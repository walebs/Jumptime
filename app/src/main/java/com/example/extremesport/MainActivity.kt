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
import com.example.extremesport.helpFunctions.loadAPIs
import com.example.extremesport.screen.*
import com.example.extremesport.ui.theme.ExtremeSportTheme
import com.example.extremesport.view.ESViewModel

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
    val viewModel = ESViewModel()
    NavHost(navController = navController, startDestination = "loading" ){
        composable("loading") { LoadingScreen(viewModel,
            loadFunction = { loadAPIs(it) },
            onNavigateToNext = { navController.navigate("main")}) }
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