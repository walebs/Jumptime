package com.example.extremesport.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.extremesport.R

@Composable
fun ReportScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .background("#1C6EAE".color)
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp)
        ) {
            Text(
                text = "LOGONAVN",
                color = Color.White
            )
            Text(
                text = "Hei",
                fontSize = 30.sp,
                color = Color.White
            )
            Text(
                text = "Gi oss feedback :)",
                color = Color.White
            )
        }
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)
                .padding(20.dp)
                .offset(y = 100.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Hvilket problem opplever du?")
                DropDownMenu()
                Spacer(modifier = Modifier.padding(40.dp))
                Text(text = "Beskriv problemet dypere")
                ReportBox()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenu() {
    var expanded by remember { mutableStateOf(false) }
    var valgt by remember { mutableStateOf("") }
    val list = stringArrayResource(R.array.ListOfProblems)
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {expanded = !expanded}
    ) {
        TextField(
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = valgt,
            onValueChange = {valgt = it},
            label = { Text(text = "Velg problem")},
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            list.forEach() { problemer ->
                DropdownMenuItem(
                    text = { Text(text = problemer) },
                    onClick = {
                        valgt = problemer
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportBox() {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = text,
        label = { Text(text = "Beskriv problem") },
        modifier = Modifier.height(200.dp),
        onValueChange = {
            text = it
        }
    )
    //Fiks farge og mulighet til at alt i tekstboks forsvinner
    TextButton(
        onClick = {
            focusManager.clearFocus()
        },
    ) {
        Text(text = "Send")
    }
}


