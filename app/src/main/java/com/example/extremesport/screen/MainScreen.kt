package com.example.extremesport.screen

import android.annotation.SuppressLint
import android.graphics.Color.parseColor
import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.extremesport.R
import com.example.extremesport.view.ESViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.security.KeyStore.TrustedCertificateEntry


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

        drawerContent = { DrawerMenu(onNavigateToNext, scaffoldState, coroutineScope) },
        drawerGesturesEnabled = false,

        bottomBar = {
            BottomAppBar(
                //cutoutShape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
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
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Map()
            }
            /*Box(Modifier.border(width = 1.dp, Color.Black, RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))) {
                ShowWeatherBox(viewModel)
            }*/
        }
    ) {
        Box(modifier = Modifier.size(395.dp, 725.dp)) {
           Map()
        }
    }
}
@Composable
fun Map(){
    val osloKlatresenter = LatLng(59.86771, 10.84170)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(osloKlatresenter, 10f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = osloKlatresenter),
            title = "osloKlatresenter",
            snippet = "Marker in osloKlatresenter"
        )
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
                modifier = Modifier.padding(start = 20.dp, top = 10.dp)
            ) {
                Text("Sted")
                Text("Værforhold")
                Text("Temp: ")
                Text("H: & L: 3")
                Text("Vindinfo: 5 m/s")
            }
            Spacer(Modifier.padding(50.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, end = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Sikkerhetsnivå")
                Image(
                    //TODO: dette må være en variabel og ikke et fast icon
                    //TODO: kommer ann på hva checkrequerments sier
                    painter = painterResource(id = R.drawable.baseline_circle_24),
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
                modifier = Modifier.padding(start = 20.dp, top = 10.dp)
            ) {
                Text("Sted")
                Text("Værforhold")
                Text("Temp: ")
                Text("H: & L: 3")
                Text("Vindinfo: 5 m/s")
            }
            Spacer(Modifier.padding(50.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, end = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Sikkerhetsnivå")
                Image(
                    //TODO: dette må være en variabel og ikke et fast icon
                    //TODO: kommer ann på hva checkrequerments sier
                    painter = painterResource(id = R.drawable.baseline_circle_24_green),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(15.dp)
                        .size(50.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        //Info om stedet
        Column(
            Modifier.padding(start = 20.dp)
        ) {
            Text("Oslo fallskjermklubb", fontSize = 20.sp)
            Column() {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.marker_png),
                        contentDescription = null,
                        Modifier.size(40.dp)
                    )
                    Text("Oslo gate 5, 0882, Oslo")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.clock),
                        contentDescription = null,
                        Modifier.size(30.dp)
                    )
                    Text("9-17")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.internett),
                        contentDescription = null,
                        Modifier.size(30.dp)
                    )
                    Text("fallskjerm.no")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_phone_24_black),
                        contentDescription = null,
                        Modifier.size(30.dp)
                    )
                    Text("876 54 321")
                }
            }
        }
    }
}

@Composable
fun DrawerMenu(onNavigateToNext: () -> Unit, scaffoldState: ScaffoldState, coroutineScope: CoroutineScope) {
    Column {
        Column(
            modifier = Modifier
                .background("#1C6EAE".color)
                .fillMaxWidth()
                .height(250.dp)
        ) {
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
                    painterResource(id = R.drawable.baseline_save_24),
                    contentDescription = "Favoritter ikon",
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
                    painterResource(id = R.drawable.baseline_archive_24),
                    contentDescription = "Arkiv",
                    modifier = Modifier
                        .size(30.dp)
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Text(text = "Arkiv", fontWeight = FontWeight.Bold)
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
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
            ) {
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
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
                    TextButton(
                        onClick = { coroutineScope.launch { scaffoldState.drawerState.close()} }
                        ) {
                        Text(text = "Tilbake -->", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    }
                }
            }
        }
    }
}

val String.color get() = Color(parseColor(this))
