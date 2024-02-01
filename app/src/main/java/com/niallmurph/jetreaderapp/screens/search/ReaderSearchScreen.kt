package com.niallmurph.jetreaderapp.screens.search

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.niallmurph.jetreaderapp.components.ReaderAppBar
import com.niallmurph.jetreaderapp.components.SearchInputField
import com.niallmurph.jetreaderapp.models.MBook

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
            ) {
                navController.popBackStack()
            }
        }
    ) {
        Column {
            SearchForm()
            Spacer(modifier = Modifier.height(16.dp))
            BookList(navController = navController)
        }
    }
}

@Composable
fun BookList(navController: NavController) {
    val list = listOf(
        MBook(
            id = "1",
            title = "The Silmarillion",
            authors = "J.R.R.Tolkein",
            notes = "Long but good"
        ), MBook(
            id = "1",
            title = "The Silmarillion",
            authors = "J.R.R.Tolkein",
            notes = "Long but good"
        ),
        MBook(
            id = "1",
            title = "The Silmarillion",
            authors = "J.R.R.Tolkein",
            notes = "Long but good"
        ),
        MBook(
            id = "1",
            title = "The Silmarillion",
            authors = "J.R.R.Tolkein",
            notes = "Long but good"
        ),
        MBook(
            id = "1",
            title = "The Silmarillion",
            authors = "J.R.R.Tolkein",
            notes = "Long but good"
        ),
        MBook(
            id = "1",
            title = "The Silmarillion",
            authors = "J.R.R.Tolkein",
            notes = "Long but good"
        )
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ){
        items(items = list) {
            BookRow(book = it, navController = navController)
        }
    }
}

@Composable
fun BookRow(book: MBook, navController: NavController) {
    Card(
        modifier = Modifier
            .clickable { }
            .fillMaxWidth()
            .height(120.dp)
            .padding(2.dp),
        shape = RectangleShape,
        elevation = 6.dp
    ){
        Row(
            modifier = Modifier
                .padding(6.dp),
            verticalAlignment = Alignment.Top
        ){
            val imageUrl = "https://www.westcountrybooks.co.uk/images/thumbs/025/0251085_9780261103573_550.jpeg"
            Image(
                painter = rememberImagePainter(data = imageUrl),
                contentDescription = "Book cover image",
                modifier = Modifier
                    .width(72.dp)
                    .fillMaxHeight()
                    .padding(end = 4.dp)
            )
            Column() {
                Text(
                    text = book.title.toString(),
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Author : ${book.authors}",
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.caption
                )
                //TODO: ADD MORE FIELDS

            }
        }
    }
}

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun SearchForm(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    hint: String = "Search",
    onSearch: (String) -> Unit = {}
) {
    Column {
        val searchQueryState = rememberSaveable { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(searchQueryState.value) { searchQueryState.value.isNotEmpty() }

        SearchInputField(
            modifier = Modifier
                .fillMaxWidth(),
            valueState = searchQueryState,
            labelId = "Search",
            enabled = true,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onSearch(searchQueryState.value.trim())
                searchQueryState.value = ""
                keyboardController?.hide()
            }
        )

    }

}
