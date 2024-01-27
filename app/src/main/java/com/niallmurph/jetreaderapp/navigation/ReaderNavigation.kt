package com.niallmurph.jetreaderapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.niallmurph.jetreaderapp.screens.SplashScreen
import com.niallmurph.jetreaderapp.screens.home.HomeScreen
import com.niallmurph.jetreaderapp.screens.login.LoginScreen

@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ReaderScreens.SplashScreen.name
    ) {
        composable(ReaderScreens.SplashScreen.name){
            SplashScreen(navController = navController)
        }
        composable(ReaderScreens.LoginScreen.name){
            LoginScreen(navController = navController)
        }
        composable(ReaderScreens.HomeScreen.name){
            HomeScreen(navController = navController)
        }
    }
}