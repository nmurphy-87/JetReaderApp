package com.niallmurph.jetreaderapp.screens.update

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.niallmurph.jetreaderapp.components.NotesInputField
import com.niallmurph.jetreaderapp.components.RatingBar
import com.niallmurph.jetreaderapp.components.ReaderAppBar
import com.niallmurph.jetreaderapp.data.DataOrException
import com.niallmurph.jetreaderapp.models.MBook
import com.niallmurph.jetreaderapp.screens.home.HomeScreenViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "ProduceStateDoesNotAssignValue")
@Composable
fun UpdateScreen(
    navController: NavController,
    bookItemId: String,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {

    Scaffold(
        topBar = {
            ReaderAppBar(
                title = "Update Book",
                icon = Icons.Default.ArrowBack,
                showProfile = false,
                navController = navController
            )
        }
    ) {
        val bookInfo = produceState<DataOrException<List<MBook>, Boolean, Exception>>(
            initialValue = DataOrException(
                data = emptyList(),
                true,
                Exception("")
            )
        ) {
            value = viewModel.data.value
        }.value

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 4.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Log.d("BookUpdateScreen", "Book info : ${viewModel.data.value.data.toString()}")
                if (bookInfo.loading == true) {
                    LinearProgressIndicator()
                    bookInfo.loading = false
                } else {
                    Surface(
                        modifier = Modifier
                            .padding(2.dp),
                        shape = CircleShape,
                        elevation = 4.dp
                    ) {
                        ShowBookUpdate(bookInfo = viewModel.data.value, bookItemId = bookItemId)
                    }
                    ShowSimpleForm(book = viewModel.data.value.data?.first { mbook ->
                        mbook.googleBookId == bookItemId
                    }!!, navController = navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ShowSimpleForm(book: MBook, navController: NavController) {

    val notesText = remember { mutableStateOf("") }
    val isStartedReading = remember { mutableStateOf(false) }
    val isFinishedReading = remember { mutableStateOf(false) }
    val ratingVal = remember { mutableStateOf(0) }

    SimpleForm(
        defaultValue = if (book.notes.toString()
                .isNotEmpty()
        ) book.notes.toString() else "No thoughts available"
    ) { note ->
        notesText.value = note
    }

    Row(
        modifier = Modifier
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        TextButton(
            onClick = { isStartedReading.value = true },
            enabled = book.startedReading == null
        ) {
            if (book.startedReading == null) {
                if (!isStartedReading.value) {
                    Text("Start Reading")
                } else {
                    Text(
                        text = "Started Reading",
                        modifier = Modifier
                            .alpha(0.6f),
                        color = Color.Red.copy(alpha = 0.5f)
                    )
                }
            } else {
                Text("Started on : ${book.startedReading}") //TODO : Format date
            }

        }

        Spacer(modifier = Modifier.width(4.dp))

        TextButton(
            onClick = { isFinishedReading.value = true },
            enabled = book.startedReading == null
        ) {
            if (book.finishedReading == null) {
                if (!isFinishedReading.value) {
                    Text("Mark as Read")
                } else {
                    Text(
                        text = "Finished Reading",
                        modifier = Modifier
                            .alpha(0.6f),
                        color = Color.Red.copy(alpha = 0.5f)
                    )
                }
            } else {
                Text("Finished on : ${book.finishedReading}") //TODO : Format date
            }

        }

    }

    Text(text = "Rating", modifier = Modifier.padding(bottom = 4.dp))
    book.rating?.toInt().let {
        RatingBar(rating = it!!) {
            ratingVal.value = it
        }
    }
}

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SimpleForm(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    defaultValue: String = "Terrible book",
    onSearch: (String) -> Unit
) {
    Column() {
        val textFieldValue = rememberSaveable { mutableStateOf(defaultValue) }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(textFieldValue.value) { textFieldValue.value.trim().isNotEmpty() }

        NotesInputField(
            modifier = modifier
                .fillMaxWidth()
                .height(160.dp)
                .padding(4.dp)
                .background(Color.White, CircleShape)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            valueState = textFieldValue,
            labelId = "Enter your thoughts",
            enabled = true,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onSearch(textFieldValue.value.trim())
                keyboardController?.hide()
            }
        )
    }
}

@Composable
fun ShowBookUpdate(bookInfo: DataOrException<List<MBook>, Boolean, Exception>, bookItemId: String) {
    Row {
        Spacer(modifier = Modifier.width(40.dp))
        if (bookInfo.data != null) {
            Column(
                modifier = Modifier
                    .padding(4.dp),
                verticalArrangement = Arrangement.Center
            ) {
                CardListItem(book = bookInfo.data!!.first { mbook ->
                    mbook.googleBookId == bookItemId
                }) {

                }
            }
        }
    }
}

@Composable
fun CardListItem(book: MBook, onPressDetails: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(
                start = 4.dp,
                end = 4.dp,
                top = 4.dp,
                bottom = 8.dp
            )
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable {

            },
        elevation = 8.dp
    ) {
        Row(
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = rememberImagePainter(
                    data = book.photoUrl.toString()
                ),
                contentDescription = "book image",
                modifier = Modifier
                    .height(120.dp)
                    .width(120.dp)
                    .padding(4.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 120.dp,
                            topEnd = 20.dp,
                            bottomEnd = 0.dp,
                            bottomStart = 0.dp
                        )
                    )
            )
            Column {
                Text(
                    text = book.title.toString(),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .padding(horizontal = 8.dp),
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = book.publishedDate.toString(),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            end = 8.dp,
                            top = 0.dp,
                            bottom = 8.dp
                        )
                )
            }
        }
    }
}
