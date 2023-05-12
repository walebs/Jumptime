package com.example.extremesport.screen

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color.parseColor
import android.media.ImageReader
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.extremesport.R
import com.example.extremesport.Screens
import com.example.extremesport.data.AppDataContainer
import com.example.extremesport.view.ESViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

val String.color get() = Color(parseColor(this))
var boolShow by mutableStateOf(false)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen (viewModel: ESViewModel, innerPadding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Map(viewModel)
        if (boolShow) {
            ShowWeatherBox(viewModel)
        }
    }
}

@Composable
fun Map(viewModel: ESViewModel) {
    val startPos = LatLng(69.67575, 18.91752)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(startPos, 6f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
    ) {
        Markers(viewModel)
    }
}

@Composable
fun Markers(viewModel: ESViewModel) {
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

    listOfPos.forEach {
        Marker(
            state = MarkerState(it),
            onClick = {
                boolShow = !boolShow // some position variable in viewmodel needs to be updated here. That way the informationBox can display different information
                true
            }
        )
    }
}

@Composable
fun ShowWeatherBox(viewModel: ESViewModel) {
    val sizeOfDevice = LocalConfiguration.current
    val screenHeight = sizeOfDevice.screenHeightDp

    val height = mapOf(
        "short" to (screenHeight/4.5).dp,
        "long" to (screenHeight-(screenHeight/4)).dp
    )
    val picture = mapOf(
        "long" to R.drawable.arrowup,
        "short" to R.drawable.arrowdown
    )
    var keyword by remember { mutableStateOf("short") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(height[keyword]!!)
            .background(Color.LightGray, RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            .alpha(1f)
            .clip(shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            .border(
                width = 1.dp,
                Color.Black,
                RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
            )
    ) {
        InformationBox(viewModel, keyword)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    keyword = if (keyword == "short") "long" else "short"
                },
                colors = ButtonDefaults.buttonColors(Color.LightGray),
                modifier = Modifier
                    .height(40.dp)
                    .width(80.dp)
                    .padding(bottom = 5.dp)
            ) {
                Image(
                    painter = painterResource(id = picture[keyword]!!),
                    contentDescription = null,
                    Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun InformationBox(viewModel: ESViewModel, keyword: String) {
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
                Text("Temp:")
                Text("H: & L:")
                Text("Vindinfo:")
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

    if (keyword == "long") {
        LongInformationBox(viewModel = viewModel)
    }
}

@Composable
fun LongInformationBox(viewModel: ESViewModel) {
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
                val days = listOf("man", "tir", "ons", "tor", "fre", "lør", "søn")
                (0..6).zip(days) { _, day ->
                    WeatherForecast(day)
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
fun WeatherForecast(
    day: String,
    weather: String = "regn",
    highTemp: Int = 0,
    lowTemp: Int = 0,
    wind: Int = 0,
    icon: Int = R.drawable.green_icon
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 2.dp)
    ) {
        Text(text = "${day}.  $weather  H: ${highTemp}\u00B0  L: ${lowTemp}\u00B0  Vindinfo: ${wind}m/s", textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.weight(4f))
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            Modifier
                .size(30.dp)
                .padding(end = 5.dp)
        )
    }
}

@Composable
fun DrawerMenu(navController: NavController, scaffoldState: ScaffoldState, coroutineScope: CoroutineScope) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    Column {
        Column(
            modifier = Modifier
                .background("#1C6EAE".color)
                .fillMaxWidth()
                .height(screenHeight - 550.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.75f),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painterResource(id = R.drawable.jumptime_logo_whiteonblue),
                        contentDescription = "Jumptime Logo",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(350.dp)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .weight(0.25f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(id = R.drawable.jumptime_tekst_whiteontransparent),
                    contentDescription = "Logonavn",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .size(150.dp)
                        .weight(1f)
                        .offset(x = (-24).dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Poeng:<X>  ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.width(IntrinsicSize.Min)
                )
            }
        }

        //Profil, scoreboards osv
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            //TODO komprimere lik kode
            TextButton(
                onClick = {
                    coroutineScope.launch { scaffoldState.drawerState.close()}
                    navController.navigate(Screens.FavorittScreen.name) {popUpTo(Screens.MainScreen.name)}
                          },
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
                onClick = {
                    coroutineScope.launch { scaffoldState.drawerState.close()}
                    navController.navigate(Screens.ArkivScreen.name) {popUpTo(Screens.MainScreen.name)}
                          },
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
                onClick = {
                    coroutineScope.launch { scaffoldState.drawerState.close()}
                    navController.navigate(Screens.SettingsScreen.name) {popUpTo(Screens.MainScreen.name)}
                          },
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
                    onClick = {
                        coroutineScope.launch { scaffoldState.drawerState.close()}
                        navController.navigate(Screens.OmOssScreen.name) {popUpTo(Screens.MainScreen.name)}
                              },
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
                        onClick = {
                            coroutineScope.launch { scaffoldState.drawerState.close()}
                            navController.navigate(Screens.ReportScreen.name) {popUpTo(Screens.MainScreen.name)}
                                  },
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
