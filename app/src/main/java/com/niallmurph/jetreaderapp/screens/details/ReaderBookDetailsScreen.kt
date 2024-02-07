package com.niallmurph.jetreaderapp.screens.details

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.niallmurph.jetreaderapp.components.ReaderAppBar
import com.niallmurph.jetreaderapp.data.Resource
import com.niallmurph.jetreaderapp.models.Item
import com.niallmurph.jetreaderapp.navigation.ReaderScreens

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BookDetailsScreen(navController: NavController,bookId :String, viewModel: DetailsViewModel = hiltViewModel()){

    Scaffold(
        topBar = {
            ReaderAppBar(
                title = "Book Details",
                icon = Icons.Default.ArrowBack,
                showProfile = false,
                navController = navController
            ) {
                navController.navigate(ReaderScreens.SearchScreen.name)
            }
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                val bookInfo = produceState<Resource<Item>>(initialValue = Resource.Loading()){
                    value = viewModel.getBookInfo(bookId = bookId)
                }.value

                if(bookInfo.data == null){
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        LinearProgressIndicator()
                    }
                } else {
                    Text(text = "Book Id = ${bookInfo.data?.volumeInfo?.title}")
                }

            }
        }
    }
}