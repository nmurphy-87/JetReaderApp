package com.niallmurph.jetreaderapp.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.niallmurph.jetreaderapp.components.*
import com.niallmurph.jetreaderapp.models.MBook
import com.niallmurph.jetreaderapp.navigation.ReaderScreens

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeScreenViewModel) {
    Scaffold(
        topBar = {
            ReaderAppBar(title = "JetReader", navController = navController)
        },
        floatingActionButton = {
            FABContent {
                navController.navigate(ReaderScreens.SearchScreen.name)
            }
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            HomeContent(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun HomeContent(
    navController: NavController = NavController(LocalContext.current),
    viewModel: HomeScreenViewModel
) {


    var listOfBooks = emptyList<MBook>()
    val currentUser = FirebaseAuth.getInstance().currentUser

    //Filtering books by current logged in user
    if (!viewModel.data.value.data.isNullOrEmpty()) {
        Log.d(
            "FIRE_SEARCH",
            "HomeScreen - isNullOrEmpty? : {${
                viewModel.data.value.data.isNullOrEmpty().toString()
            }}"
        )
        listOfBooks = viewModel.data.value.data?.toList()!!.filter { mBook ->
            mBook.userId == currentUser?.uid.toString()
        }
    }

    val currentUserName = if (!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) {
        FirebaseAuth.getInstance().currentUser?.email?.split("@")?.get(0)
    } else {
        "User"
    }
    Column(
        modifier = Modifier
            .padding(2.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Start)
        ) {
            TitleSection(label = "Your reading \nactivity right now ...")
            Spacer(modifier = Modifier.fillMaxWidth(0.7f))
            Column {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .clickable {
                            navController.navigate(ReaderScreens.StatsScreen.name)
                        }
                        .size(48.dp),
                    tint = MaterialTheme.colors.secondaryVariant
                )
                Text(
                    text = currentUserName!!,
                    modifier = Modifier
                        .padding(2.dp),
                    style = MaterialTheme.typography.overline,
                    color = Color.Red,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Clip
                )
                Divider()
            }
        }
        ReadingRightNowArea(
            books = listOfBooks,
            navController = navController
        )
    }
}

@Composable
fun ReadingRightNowArea(
    books: List<MBook>, navController: NavController
) {
    val readingNowList = books.filter { mBook ->
        mBook.startedReading != null && mBook.finishedReading == null
    }
    
    HorizontalScrollableComponent(listOfBooks = readingNowList){
        navController.navigate(ReaderScreens.UpdateScreen.name + "/$it")
    }
    TitleSection(label = "Reading List")
    BookListArea(listOfBooks = books, navController = navController)
}

@Composable
fun BookListArea(listOfBooks: List<MBook>, navController: NavController) {

    val addedBooks = listOfBooks.filter { mBook ->
        mBook.startedReading == null && mBook.finishedReading == null
    }

    HorizontalScrollableComponent(addedBooks) {
        navController.navigate(ReaderScreens.UpdateScreen.name + "/$it")
    }
}

@Composable
fun HorizontalScrollableComponent(
    listOfBooks: List<MBook>,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onCardPressed: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(280.dp)
            .horizontalScroll(scrollState)
    ) {

        if (viewModel.data.value.loading == true) {
            LinearProgressIndicator()
        } else {
            if (listOfBooks.isNullOrEmpty()) {
                Surface(modifier = Modifier
                    .padding(24.dp)
                ) {
                    Text(
                        text = "No books found, add a book",
                        style = TextStyle(
                            color = Color.Red.copy(
                                alpha = 0.4f
                            ),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    )
                }
            } else {
                
            }
        }
        for (book in listOfBooks) {
            ListCard(book) {
                onCardPressed(book.googleBookId.toString())
            }
        }
    }
}

