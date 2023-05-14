package com.example.extremesport.screen

import android.annotation.SuppressLint
import android.graphics.Color.parseColor
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.extremesport.R
import com.example.extremesport.Screens
import com.example.extremesport.model.LocationData
import com.example.extremesport.model.RequirementsResult
import com.example.extremesport.view.ESViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.round

val String.color get() = Color(parseColor(this))
var boolShow by mutableStateOf(false)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter", "SimpleDateFormat")
@Composable
fun MainScreen (viewModel: ESViewModel, innerPadding: PaddingValues) {
    var currentMarkerId by remember { mutableStateOf("") }
    var clickedMarker: Marker? by remember { mutableStateOf(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Map(viewModel,
            onClick = { marker: Marker ->
                boolShow = !boolShow //|| currentMarkerId != marker.id // some position variable in viewmodel needs to be updated here. That way the informationBox can display different information
                clickedMarker = marker
                currentMarkerId = marker.id
                if (boolShow) {
                    val sdf = SimpleDateFormat("yyyy-MM-dd")
                    val currentDate = sdf.format(Date())
                    //TODO updatere når marker er trykket på?
                    val oldState = viewModel.esState.value
                    viewModel.update(marker.position.latitude, marker.position.longitude, 1, 1200, currentDate, "+01:00")
                    while (oldState == viewModel.esState.value) {
                        Thread.sleep(1)
                    }
                }
                //vente til API-ene har oppdatert seg, kanskje bruke loadAPI fra loadingskjerm?
                true
            }
        )
        if (boolShow) {
            val info = viewModel.getInfo()
            val jsonInfo = viewModel.returnLocations()?.locations?.find { it.latitude == clickedMarker?.position?.latitude && it.longitude == clickedMarker?.position?.longitude }
            val checkReq = viewModel.checkRequirements("Fallskjermhopping")
            ShowWeatherBox(
                info,
                checkReq,
                jsonInfo
            )
        }
    }
}

@Composable
fun Map(viewModel: ESViewModel, onClick: (Marker) -> Boolean) {
    val startPos = LatLng(59.9138, 10.7387)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(startPos, 6f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
    ) {
        Markers(viewModel, onClick = onClick)
    }
}

@Composable
fun Markers(viewModel: ESViewModel, onClick: (Marker) -> Boolean) {
    val jsonData = viewModel.returnLocations()

    jsonData?.locations?.forEach { location ->
        Marker(
            MarkerState(LatLng(location.latitude, location.longitude)),
            onClick = { marker ->
                onClick(marker)
            }
        )
    }
}

@Composable
fun ShowWeatherBox(
    info: RequirementsResult,
    checkReq: Double,
    jsonInfo: LocationData.Location?,
) {
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

    val icons = listOf(R.drawable.red_icon, R.drawable.yellow_icon, R.drawable.green_icon)
    val icon = icons[round(checkReq).toInt()]

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(height[keyword]!!)
            .background("#1C6EAE".color, RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            .alpha(1f)
            .clip(shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            .border(
                width = 1.dp,
                Color.Black,
                RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
            )
    ) {
        InformationBox(keyword, icon, info, jsonInfo)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background("#1C6EAE".color),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    keyword = if (keyword == "short") "long" else "short"
                },
                colors = ButtonDefaults.buttonColors("#1C6EAE".color),
                modifier = Modifier
                    .height(40.dp)
                    .width(80.dp)
                    .padding(bottom = 5.dp)
            ) {
                Icon(
                    painter = painterResource(id = picture[keyword]!!),
                    contentDescription = null,
                    Modifier.fillMaxSize(),
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun InformationBox(
    keyword: String,
    icon: Int,
    info: RequirementsResult,
    jsonInfo: LocationData.Location?,
) {
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
                if (jsonInfo != null) {
                    Text(jsonInfo.name, color = Color.White)
                }
                Text(info.summaryCode1, color = Color.White)
                Text("${info.currentTemp.toInt()}°", color = Color.White)
                Text("${info.windStrength} m/s", color = Color.White)
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, end = 20.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text("Sikkerhetsnivå", Modifier.padding(top = 10.dp), color = Color.White)
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(15.dp)
                        .size(50.dp)
                )
            }
        }
    }

    if (keyword == "long") {
        LongInformationBox(icon, info, jsonInfo)
    }
}

@Composable
fun LongInformationBox(
    icon: Int,
    info: RequirementsResult,
    jsonInfo: LocationData.Location?
) {
    Spacer(modifier = Modifier.height(15.dp))
    //Info om stedet
    Column(
        Modifier.padding(start = 20.dp)
    ) {
        if (jsonInfo != null) {
            Text(jsonInfo.name,
                Modifier.padding(bottom = 5.dp),
                fontSize = 20.sp,
                color = Color.White
            )
        }
        Column(Modifier.padding(bottom = 10.dp)) {
            if (jsonInfo != null) {
                LocationInfo(R.drawable.marker, jsonInfo.adress)
            }
            if (jsonInfo != null) {
                LocationInfo(R.drawable.clock_icon, jsonInfo.openingtime)
            }
            if (jsonInfo != null) {
                LocationInfo(R.drawable.internett_icon, jsonInfo.website)
            }
            if (jsonInfo != null) {
                LocationInfo(R.drawable.phone_icon, jsonInfo.phoneNr)
            }
        }
        Column(
            Modifier.fillMaxWidth()
        ) {
            Text(text = "Dagsvarsel",
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                color = Color.White
            )
            Column(Modifier.fillMaxWidth()) {
                val times = listOf("next 1h", "next 6h", "next 12h")
                for (time in times) {
                    when (time) {
                        "next 1h" -> WeatherForecast(time, info.summaryCode1, info.highTemp1.toInt(), info.lowTemp1.toInt(), info.windStrength, icon)
                        "next 6h" -> WeatherForecast(time, info.summaryCode6, info.highTemp6.toInt(), info.lowTemp6.toInt(), info.windStrength, icon)
                        "next 12h" -> WeatherForecast(time, info.summaryCode12, info.highTemp12.toInt(), info.lowTemp12.toInt(), info.windStrength, icon)
                    }
                }
            }
        }
    }
}

@Composable
fun LocationInfo(icon: Int, str: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            Modifier
                .size(30.dp)
                .padding(end = 5.dp)
        )
        Text(str, color = Color.White)
    }
}

@Composable
fun WeatherForecast(
    time: String,
    weather: String = "regn",
    highTemp: Int,
    lowTemp: Int,
    wind: Double,
    icon: Int = R.drawable.green_icon
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 2.dp)
    ) {
        Text(
            text = "$time  $weather  H: ${highTemp}\u00B0  L: ${lowTemp}\u00B0  $wind m/s",
            textAlign = TextAlign.Center,
            color = Color.White
        )
        Spacer(modifier = Modifier.weight(4f))
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            Modifier
                .size(30.dp)
                .padding(end = 10.dp)
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
