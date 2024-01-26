package com.niallmurph.jetreaderapp.navigation

import com.niallmurph.jetreaderapp.screens.SplashScreen

enum class ReaderScreens {
    SplashScreen,
    LoginScreen,
    CreateAccountScreen,
    HomeScreen,
    DetailsScreen,
    StatsScreen,
    SearchScreen,
    UpdateScreen;
    companion object{
        fun fromRoute(route : String?) : ReaderScreens
        = when (route?.substringBefore("/")){
            SplashScreen.name -> SplashScreen
            LoginScreen.name -> LoginScreen
            CreateAccountScreen.name -> CreateAccountScreen
            HomeScreen.name -> HomeScreen
            DetailsScreen.name -> DetailsScreen
            StatsScreen.name -> StatsScreen
            SearchScreen.name -> SearchScreen
            UpdateScreen.name -> UpdateScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognised")
        }
    }
}
