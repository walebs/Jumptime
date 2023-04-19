package com.example.extremesport.screen

import android.annotation.SuppressLint
import android.graphics.Color.parseColor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.extremesport.R
import com.example.extremesport.view.ESViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen (onNavigateToNext: () -> Unit ) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    //val viewModel = ESViewModel()
    //Thread.sleep(5000)
    println("vente 5sek\n\n")
    //val testBoolean  = viewModel.checkRequirements("Fallskjermhopping")
    //val testBoolean2  = viewModel.checkRequirements("Testing")

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.size(60.dp),
                //contentColor = Color.White,
                onClick = {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState
                            .showSnackbar("hei")
                        }
                     },
                //backgroundColor = Color.Black
            )
            {
                Icon(imageVector = Icons.Default.Add, null)
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,

        drawerContent = { DrawerMenu(onNavigateToNext) },
        drawerGesturesEnabled = false,

        bottomBar = {
            BottomAppBar(
                cutoutShape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
                //backgroundColor = "#59FF48".color
            )
            {
                BottomNavigationItem(
                    selected = false,
                    onClick = { coroutineScope.launch { scaffoldState.drawerState.open()} },
                    selectedContentColor = Color.Red,
                    unselectedContentColor = Color.White,
                    icon = {
                        Icon(painterResource(id = R.drawable.hamburger), null)
                    }
                )

                Spacer(modifier = Modifier.padding(130.dp))

                BottomNavigationItem(
                    selected = false,
                    onClick = onNavigateToNext,
                    selectedContentColor = Color.Red,
                    unselectedContentColor = Color.White,
                    icon = {
                        Icon(painterResource(id = R.drawable.settings), null)
                    }
                )
            }
        }
    ) {
        Box(modifier = Modifier.size(395.dp, 770.dp)) {
           Map()
        }
    }
}
@Composable
fun Map(){
    val tromsoo = LatLng(69.67575, 18.91752)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(tromsoo, 10f)
    }

    var uiSettings by remember { mutableStateOf(MapUiSettings()) }

    val googleMap = GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        uiSettings =  MapUiSettings()
    ) {
        Markers()
    }
}

@Composable
fun Markers(){
    val tromsoo = LatLng(69.67575, 18.91752)
    val troms = LatLng(69.05894, 18.54549)
    val bodoo = LatLng(67.27268, 14.41794)
    val ntnu = LatLng(63.89993, 10.36208)
    val oppdal = LatLng(62.65002, 9.85408)
    val fooniks = LatLng(62.74936, 7.26345)
    val lesja = LatLng(62.23288, 8.25007)

    Marker(
        state = MarkerState(position = tromsoo)
    )
    Marker(
        state = MarkerState(position = troms)
    )
    Marker(
        state = MarkerState(position = bodoo)
    )
    Marker(
        state = MarkerState(position = ntnu)
    )
    Marker(
        state = MarkerState(position = oppdal)
    )
    Marker(
        state = MarkerState(position = fooniks)
    )
    Marker(
        state = MarkerState(position = lesja)
    )
}


@Composable
fun DrawerMenu(onNavigateToNext: () -> Unit ){
            Box(
                modifier = Modifier
                    .background(color = "#ff6200EE".color)
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
