package com.example.datecalculator

import DateCalculatorScreen
import DateModifierScreen
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DateCalculatorApp()
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateCalculatorApp() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.app_name), fontSize = 32.sp)
                },
                actions = {
                    IconButton(onClick = { navController.navigate("dateCalculator") }) {
                        Icon(Icons.Rounded.DateRange, contentDescription = "Нахождение интервала")
                    }
                    IconButton(onClick = { navController.navigate("dateModifier") }) {
                        Icon(Icons.Rounded.Search, contentDescription = "Операции с датой")
                    }
                }


            )
        }
    ) {
        NavHost(navController, startDestination = "dateCalculator") {
            composable("dateCalculator") {
                DateCalculatorScreen(navController)
            }
            composable("dateModifier") {
                DateModifierScreen()
            }
        }
    }
}