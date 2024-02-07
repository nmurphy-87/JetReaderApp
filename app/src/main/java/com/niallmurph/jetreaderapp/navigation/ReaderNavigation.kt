package com.niallmurph.jetreaderapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.niallmurph.jetreaderapp.screens.SplashScreen
import com.niallmurph.jetreaderapp.screens.details.BookDetailsScreen
import com.niallmurph.jetreaderapp.screens.home.HomeScreen
import com.niallmurph.jetreaderapp.screens.home.HomeScreenViewModel
import com.niallmurph.jetreaderapp.screens.login.LoginScreen
import com.niallmurph.jetreaderapp.screens.search.BookSearchViewModel
import com.niallmurph.jetreaderapp.screens.search.SearchScreen
import com.niallmurph.jetreaderapp.screens.stats.StatsScreen

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
            val homeViewModel= hiltViewModel<HomeScreenViewModel>()
            HomeScreen(navController = navController,viewModel = homeViewModel)
        }
        composable(ReaderScreens.SearchScreen.name) {
            val viewModel = hiltViewModel<BookSearchViewModel>()
            SearchScreen(navController = navController, viewModel = viewModel)
        }
        composable(ReaderScreens.StatsScreen.name){
            StatsScreen(navController = navController)
        }
        val detailName = ReaderScreens.DetailsScreen.name
        composable("$detailName/{bookId}", arguments = listOf(navArgument("bookId"){
            type = NavType.StringType
        })){ backStack ->
            backStack.arguments?.getString("bookId").let { id -> BookDetailsScreen(navController = navController, bookId = id.toString()) }
        }
    }
}