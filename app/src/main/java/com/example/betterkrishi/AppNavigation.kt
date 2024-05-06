package com.example.betterkrishi

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.betterkrishi.screens.ImagePickerScreen
import com.example.betterkrishi.screens.WelcomeScreen

@Composable
fun navigator() {
    val navController = rememberNavController()
    val navHost = NavHost(navController = navController, startDestination = "login"){
        composable(route = "home"){

        }
        composable(route = "sign up"){
        }
        composable (route = "login"){
            WelcomeScreen()
        }
    }

}
