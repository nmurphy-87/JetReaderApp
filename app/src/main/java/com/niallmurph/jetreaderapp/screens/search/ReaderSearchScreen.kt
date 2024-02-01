package com.niallmurph.jetreaderapp.screens.search

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.niallmurph.jetreaderapp.components.ReaderAppBar

@Preview
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreen(navController: NavController = NavController(LocalContext.current)) {

    Scaffold(
        topBar = {
            ReaderAppBar(
                title = "Search Books",
                icon = Icons.Default.ArrowBack,
                showProfile = false,
                navController = navController
            ){
                navController.popBackStack()
            }
        }
    ){

    }
}