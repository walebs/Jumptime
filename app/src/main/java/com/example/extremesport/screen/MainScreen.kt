package com.example.extremesport.screen

import android.annotation.SuppressLint
import android.graphics.Color.parseColor
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
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
    val viewModel = ESViewModel()
    //Thread.sleep(5000)
    //println("vente 5sek\n\n")
    //val testBoolean  = viewModel.checkRequirements("Fallskjermhopping")
    //val testBoolean2  = viewModel.checkRequirements("Testing")
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .size(80.dp)
                    .border(
                        BorderStroke(1.dp, Color.Black),
                        shape = CircleShape
                    )
                    .clip(CircleShape),
                contentColor = Color.Black,
                onClick = {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState
                            .showSnackbar("hei")
                        }
                     },
                backgroundColor = Color.White
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
                //cutoutShape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
                modifier = Modifier
                    .height(65.dp),
                backgroundColor = "#1C6EAE".color
            )
            {
                BottomNavigationItem(
                    selected = false,
                    onClick = { coroutineScope.launch { scaffoldState.drawerState.open()} },
                    selectedContentColor = Color.Red,
                    unselectedContentColor = Color.White,
                    icon = {
                        Icon(
                            painterResource(id = R.drawable.baseline_menu_24_white),
                            contentDescription = "Menyknapp",
                            modifier = Modifier
                                .size(40.dp)
                        )
                    }
                )

                Spacer(modifier = Modifier.padding(130.dp))

                BottomNavigationItem(
                    selected = false,
                    onClick = onNavigateToNext,
                    selectedContentColor = Color.Red,
                    unselectedContentColor = Color.White,
                    icon = {
                        Icon(
                            painterResource(id = R.drawable.baseline_settings_24_white),
                            contentDescription = "Settingsknapp",
                            modifier = Modifier
                                .size(40.dp)
                        )
                    }
                )
            }
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Map()
                ShowWeatherBox(viewModel)
            }
        }
    )
}
@Composable
fun Map(){
    val tromsoo = LatLng(69.67575, 18.91752)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(tromsoo, 6f)
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
        //, onClick = {  }
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
fun ShowWeatherBox(viewModel: ESViewModel) {
    val sizeOfDevice = LocalConfiguration.current
    val screenHeight = sizeOfDevice.screenHeightDp

    var height by remember { mutableStateOf((screenHeight/4).dp) }
    var picture by remember { mutableStateOf(R.drawable.arrowdown) }
    var buttonText by remember { mutableStateOf("Show more") }
    var keyword by remember { mutableStateOf("short") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(Color.Blue, RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            .alpha(1f)
            .clip(shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            .padding(5.dp)
    ) {
        InformationBox(keyword)
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    if (height == (screenHeight/4).dp) {
                        height = (screenHeight-(screenHeight/4)).dp
                        picture = R.drawable.arrowup
                        keyword = "long"
                        buttonText = "Show less"
                    } else {
                        height = (screenHeight/4).dp
                        picture = R.drawable.arrowdown
                        keyword = "short"
                        buttonText = "Show more"
                    }
                },
                Modifier
                    .height(40.dp)
                    .width(150.dp)
            ) {
                Text(buttonText)
                Image(painter = painterResource(id = picture), contentDescription = null, Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun InformationBox(keyword:String) {
    when (keyword) {
        "short" -> {
            Column(
                Modifier.background(Color.Green).fillMaxWidth()
            ) {
                Text("short")
            }
        }
        "long" -> {
            Column(
                Modifier.background(Color.Red).fillMaxWidth()
            ) {
                Text("long")
            }
        }
        else -> {

        }
    }
}

@Composable
fun DrawerMenu(onNavigateToNext: () -> Unit ) {
    Column {
        Column(
            modifier = Modifier
                .background("#1C6EAE".color)
                .fillMaxWidth()
                .height(250.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .size(250.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        //idk hvorfor den ikke blir hvit
                        painterResource(id = R.drawable.baseline_account_circle_24_white),
                        contentDescription = "Profilbilde",
                        modifier = Modifier
                            .size(150.dp)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "<NAVN>",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    color = Color.White,
                    modifier = Modifier.width(IntrinsicSize.Min)
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Poeng:  ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier.width(IntrinsicSize.Min)
                    )
                    Text(
                        text = "<x>",
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        color = Color.White,
                        modifier = Modifier.width(IntrinsicSize.Min)
                    )
                }
            }
        }
        //Profil, scoreboards osv
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextButton(
                onClick = { /*TODO*/ },
            ) {
                Image(
                    painterResource(id = R.drawable.baseline_account_circle_24),
                    contentDescription = "Profilbilde ikon",
                    modifier = Modifier
                        .size(30.dp)
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Text(text = "Profil", fontWeight = FontWeight.Bold)
            }
            TextButton(
                onClick = { /*TODO*/ },
            ) {
                Image(
                    painterResource(id = R.drawable.baseline_scoreboard_24),
                    contentDescription = "Scoreboard ikon",
                    modifier = Modifier
                        .size(30.dp)
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Text(text = "Scoreboard", fontWeight = FontWeight.Bold)
            }
            TextButton(
                onClick = { /*TODO*/ },
            ) {
                Image(
                    painterResource(id = R.drawable.baseline_save_24),
                    contentDescription = "Favoritt ikon",
                    modifier = Modifier
                        .size(30.dp)
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Text(text = "Favoritter", fontWeight = FontWeight.Bold)
            }
            TextButton(
                onClick = { /*TODO*/ },
            ) {
                Image(
                    painterResource(id = R.drawable.baseline_settings_24),
                    contentDescription = "Innstillinger ikon",
                    modifier = Modifier
                        .size(30.dp)
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Text(text = "Innstillinger", fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.padding(120.dp))
                TextButton(
                    onClick = { /*TODO*/ },
                ) {
                    Image(
                        painterResource(id = R.drawable.baseline_groups_24),
                        contentDescription = "Om oss ikon",
                        modifier = Modifier
                            .size(30.dp)
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(text = "Om oss", fontWeight = FontWeight.Bold)
                }
                TextButton(
                    onClick = { /*TODO*/ },
                ) {
                    Image(
                        painterResource(id = R.drawable.baseline_report_problem_24),
                        contentDescription = "Rapporter ikon",
                        modifier = Modifier
                            .size(30.dp)
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(text = "Rapporter", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

val String.color get() = Color(parseColor(this))
