package com.niallmurph.jetreaderapp.screens.stats

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.sharp.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.niallmurph.jetreaderapp.components.BookRowStats
import com.niallmurph.jetreaderapp.components.ReaderAppBar
import com.niallmurph.jetreaderapp.models.MBook
import com.niallmurph.jetreaderapp.screens.home.HomeScreenViewModel
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun StatsScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    var books: List<MBook>
    val currentUser = FirebaseAuth.getInstance().currentUser

    Scaffold(
        topBar = {
            ReaderAppBar(
                title = "Book Stats",
                icon = Icons.Default.ArrowBack,
                showProfile = false,
                navController = navController
            ) {
                navController.popBackStack()
            }
        }
    ) {
        Surface {
            books = if (!viewModel.data.value.data.isNullOrEmpty()) {
                viewModel.data.value.data!!.filter { mBook ->
                    (mBook.userId == currentUser?.uid)
                }
            } else {
                emptyList()
            }

            val readBooksList: List<MBook> =
                if (!viewModel.data.value.data.isNullOrEmpty()) {
                    books.filter { mBook ->
                        (mBook.userId == currentUser?.uid) && (mBook.finishedReading != null)
                    }
                } else {
                    emptyList()
                }

            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(48.dp)
                            .padding(2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Sharp.Person,
                            contentDescription = "icon"
                        )
                    }
                    Text(
                        text = "Hi, ${
                            currentUser?.email.toString().split("@")[0].uppercase(
                                Locale.ROOT
                            )
                        }"
                    )
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    shape = CircleShape,
                    elevation = 4.dp
                ) {

                    val readingBooksList: List<MBook> =
                        if (!viewModel.data.value.data.isNullOrEmpty()) {
                            books.filter { mBook ->
                                (mBook.startedReading != null) && (mBook.finishedReading == null)
                            }
                        } else {
                            emptyList()
                        }

                    Column(
                        modifier = Modifier
                            .padding(start = 24.dp, top = 4.dp, bottom = 4.dp),
                        horizontalAlignment = Alignment.Start
                    ){
                        Text(text = "Your Stats", style = MaterialTheme.typography.h5)
                        Divider()
                        Text(text = "You're reading : ${readingBooksList.size.toString()} books")
                        Text(text = "You've read: ${readBooksList.size.toString()} books")
                    }
                }
                if(viewModel.data.value.loading == true) {
                    LinearProgressIndicator()
                } else {
                    Divider()
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentPadding = PaddingValues(12.dp)
                    ) {
                        items(items = readBooksList){mBook ->
                            BookRowStats(book = mBook)
                        }
                    }
                }
            }
        }
    }
}