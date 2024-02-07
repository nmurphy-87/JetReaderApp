package com.niallmurph.jetreaderapp.screens.search

import android.annotation.SuppressLint
import android.graphics.fonts.FontStyle
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.niallmurph.jetreaderapp.components.ReaderAppBar
import com.niallmurph.jetreaderapp.components.SearchInputField
import com.niallmurph.jetreaderapp.models.Item

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreen(navController: NavController, viewModel: BookSearchViewModel) {

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
            SearchForm(
                viewModel = viewModel
            ) { query ->
                viewModel.searchBooks(query)
            }
            Spacer(modifier = Modifier.height(16.dp))
            BookList(navController = navController, viewModel = viewModel)
        }
    }
}

@Composable
fun BookList(navController: NavController, viewModel: BookSearchViewModel) {
    val list = viewModel.list

    if(viewModel.isLoading){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LinearProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(items = list) {
                BookRow(book = it, navController = navController)
            }
        }
    }
}

@Composable
fun BookRow(book: Item, navController: NavController) {
    Card(
        modifier = Modifier
            .clickable { }
            .fillMaxWidth()
            .height(120.dp)
            .padding(2.dp),
        shape = RectangleShape,
        elevation = 6.dp
    ) {
        Row(
            modifier = Modifier
                .padding(6.dp),
            verticalAlignment = Alignment.Top
        ) {
            val imageUrl =
                if (book.volumeInfo.imageLinks.thumbnail.isEmpty()) "https://minalsampat.com/wp-content/uploads/2019/12/book-placeholder.jpg" else book.volumeInfo.imageLinks.thumbnail
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
                    text = book.volumeInfo.title,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Author : ${book.volumeInfo.authors}",
                    overflow = TextOverflow.Clip,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    style = MaterialTheme.typography.caption
                )
                Text(
                    text = "Date : ${book.volumeInfo.publishedDate}",
                    overflow = TextOverflow.Clip,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    style = MaterialTheme.typography.caption
                )
                Text(
                    text = "Category : ${book.volumeInfo.categories}",
                    overflow = TextOverflow.Clip,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    style = MaterialTheme.typography.caption
                )

            }
        }
    }
}

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchForm(
    modifier: Modifier = Modifier,
    viewModel: BookSearchViewModel,
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
