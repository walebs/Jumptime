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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.extremesport.R
import com.example.extremesport.Screens
import com.example.extremesport.view.ESViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

var boolShow by mutableStateOf(false)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen (navController: NavController, viewModel: ESViewModel) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

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

        drawerContent = { DrawerMenu(navController, scaffoldState, coroutineScope) },
        drawerGesturesEnabled = false,

        bottomBar = {
            BottomAppBar(
                //TODO skal den være her?
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
                    onClick =  { navController.navigate(Screens.SettingsScreen.name) {popUpTo(Screens.MainScreen.name)} },
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
                if (boolShow) {
                    ShowWeatherBox(viewModel)
                }
            }
        }
    )
}

@Composable
fun Map() {
    val startPos = LatLng(69.67575, 18.91752)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(startPos, 6f)
    }

    var uiSettings by remember { mutableStateOf(MapUiSettings()) }

    var googleMap = GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        uiSettings =  MapUiSettings()
    ) {
        Markers()
    }
}

@Composable
fun Markers() {
    //TODO hente fra JSON filen
    val listOfPos = listOf (
        LatLng(69.67575, 18.91752), //tromsoo
        LatLng(69.05894, 18.54549), //troms
        LatLng(67.27268, 14.41794), //bodoo
        LatLng(63.89993, 10.36208), //ntnu
        LatLng(62.65002, 9.85408),  //oppdal
        LatLng(62.74936, 7.26345),  //fooniks
        LatLng(62.23288, 8.25007),  //lesja
    )

    for (pos in listOfPos) {
        Marker(
            state = MarkerState(pos),
            onClick = {
                boolShow = !boolShow
                true
            }
        )
    }
}

@Composable
fun ShowWeatherBox(viewModel: ESViewModel) {
    val sizeOfDevice = LocalConfiguration.current
    val screenHeight = sizeOfDevice.screenHeightDp

    var height by remember { mutableStateOf((screenHeight/4.5).dp) }
    var picture by remember { mutableStateOf(R.drawable.arrowdown) }
    var keyword by remember { mutableStateOf("short") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(Color.LightGray, RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            .alpha(1f)
            .clip(shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            .border(
                width = 1.dp,
                Color.Black,
                RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
            )
    ) {
        InformationBox(keyword, viewModel)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    if (height == (screenHeight/4.5).dp) {
                        height = ((screenHeight-(screenHeight/5))+20).dp
                        picture = R.drawable.arrowup
                        keyword = "long"
                    } else {
                        height = (screenHeight/4.5).dp
                        picture = R.drawable.arrowdown
                        keyword = "short"
                    }
                },
                colors = androidx.compose.material.ButtonDefaults.buttonColors(Color.Gray),
                modifier = Modifier
                    .height(40.dp)
                    .width(80.dp)
                    .padding(bottom = 5.dp)
            ) {
                Image(
                    painter = painterResource(id = picture),
                    contentDescription = null,
                    Modifier.fillMaxSize()
                )
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun InformationBox(keyword: String, viewModel: ESViewModel) {
    when (keyword) {
        "short" -> {
            ShortInformationBox(viewModel = viewModel)
        }
        "long" -> {
            LongInformationBox(viewModel = viewModel)
        }
        else -> {

        }
    }
}

@Composable
fun ShortInformationBox(viewModel: ESViewModel) {
    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Row(
            Modifier
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(start = 25.dp, top = 15.dp)
            ) {
                Text("Sted")
                Text("Værforhold")
                Text("Temp: ")
                Text("H: & L: ")
                Text("Vindinfo: ")
            }
            Spacer(Modifier.padding(50.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, end = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Sikkerhetsnivå", Modifier.padding(top = 10.dp))
                Image(
                    //TODO: dette må være en variabel og ikke et fast icon
                    //TODO: kommer ann på hva checkrequerments sier
                    painter = painterResource(id = R.drawable.green_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(15.dp)
                        .size(50.dp)
                )
            }
        }
    }
}

@Composable
fun LongInformationBox(viewModel: ESViewModel) {
    ShortInformationBox(viewModel = viewModel)

    Spacer(modifier = Modifier.height(15.dp))
    //Info om stedet
    Column(
        Modifier.padding(start = 20.dp)
    ) {
        Text("Oslo fallskjermklubb", Modifier.padding(bottom = 5.dp), fontSize = 20.sp)
        Column(Modifier.padding(bottom = 10.dp)) {
            LocationInfo(R.drawable.marker, "Oslo gate 5, 0882, Oslo")
            LocationInfo(R.drawable.clock_icon, "9-17")
            LocationInfo(R.drawable.internett_icon, "fallskjerm.no")
            LocationInfo(R.drawable.phone_icon, "876 54 321")
        }
        Column(
            Modifier.fillMaxWidth()
        ) {
            Text(text = "7 - Dagersvarsel",
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp), textAlign = TextAlign.Center, fontSize = 20.sp)
            Column(Modifier.fillMaxWidth()) {
                for(i in 0..6) {
                    WeatherForecast()
                }
            }
        }
    }
}

@Composable
fun LocationInfo(icon: Int, str: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            Modifier
                .size(30.dp)
                .padding(end = 5.dp)
        )
        Text(str)
    }
}

@Composable
fun WeatherForecast() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 2.dp)
    ) {
        //TODO dette skal være variabler og alle verdiene skal trolig hentes fra den ferdige checkrequerment fun
        Text(text = "man.  regn  H: 5\u00B0  L: 1\u00B0  Vindinfo: 7 m/s", textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.weight(4f))
        Image(
            painter = painterResource(id = R.drawable.green_icon),
            contentDescription = null,
            Modifier
                .size(30.dp)
                .padding(end = 5.dp)
        )
    }
}

@Composable
fun DrawerMenu(navController: NavController, scaffoldState: ScaffoldState, coroutineScope: CoroutineScope) {
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
                        painterResource(id = R.drawable.baseline_account_circle_24_white),
                        contentDescription = "Profilbilde",
                        modifier = Modifier
                            .size(150.dp),
                        tint = Color.White
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
                    text = "JumpTime",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = Color.White,
                    modifier = Modifier
                        .width(IntrinsicSize.Min)
                        .padding(end = 6.dp, start = 6.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(10.dp)
                ) {
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
            //TODO komprimere lik kode
            TextButton(
                onClick = { navController.navigate(Screens.FavorittScreen.name) {popUpTo(Screens.MainScreen.name)} },
            ) {
                Image(
                    painterResource(id = R.drawable.baseline_save_24),
                    contentDescription = "Favoritter ikon",
                    modifier = Modifier
                        .size(30.dp)
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Text(text = "Favoritter", fontWeight = FontWeight.Bold)
            }
            TextButton(
                onClick = { navController.navigate(Screens.ArkivScreen.name) {popUpTo(Screens.MainScreen.name)} },
            ) {
                Image(
                    painterResource(id = R.drawable.baseline_archive_24),
                    contentDescription = "Arkiv",
                    modifier = Modifier
                        .size(30.dp)
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Text(text = "Arkiv", fontWeight = FontWeight.Bold)
            }
            TextButton(
                onClick = { navController.navigate(Screens.SettingsScreen.name) {popUpTo(Screens.MainScreen.name)} },
            ) {
                Image(
                    painterResource(id = R.drawable.baseline_settings_24),
                    contentDescription = "Innstillinger ikon",
                    modifier = Modifier
                        .size(30.dp)
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Text(text = "Innstillinger", fontWeight = FontWeight.Bold)
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
            ) {
                TextButton(
                    onClick = { navController.navigate(Screens.OmOssScreen.name) {popUpTo(Screens.MainScreen.name)} },
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(
                        onClick = { navController.navigate(Screens.ReportScreen.name) {popUpTo(Screens.MainScreen.name)} },
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
                    TextButton(
                        onClick = { coroutineScope.launch { scaffoldState.drawerState.close()} }
                        ) {
                        Text(text = "Tilbake", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    }
                }
            }
        }
    }
}

val String.color get() = Color(parseColor(this))