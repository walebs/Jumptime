package com.example.extremesport.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.extremesport.R

@Composable
fun ArkivScreen(navController: NavController){
    Column {
        Text(text = "Arkiv")
        Cards()
    }
}

@Composable
fun Cards() {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "StasjonNavn",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp)
                Rating(rating = 3)
            }

        }

    }
}

@Composable
fun Rating(rating: Int) {
    var ratingState by remember { mutableStateOf(rating) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..5) {
            Icon(
                painterResource(id = R.drawable.baseline_star_border_24_white),
                contentDescription = "Tom stjerne",
                modifier = Modifier.clickable {
                    ratingState = i
                },
                tint = if (i <= ratingState) Color.Yellow else Color.LightGray
            )
        }
    }
}