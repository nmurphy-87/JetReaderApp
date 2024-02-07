package com.niallmurph.jetreaderapp.screens.details

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.niallmurph.jetreaderapp.components.ReaderAppBar
import com.niallmurph.jetreaderapp.components.RoundedButton
import com.niallmurph.jetreaderapp.data.Resource
import com.niallmurph.jetreaderapp.models.Item
import com.niallmurph.jetreaderapp.models.MBook
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
                        Text(text = "Loading ...")
                        LinearProgressIndicator()
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        ShowBookDetails(bookInfo = bookInfo, navController = navController)
                    }
                }

            }
        }
    }
}

@Composable
fun ShowBookDetails(bookInfo : Resource<Item>, navController: NavController){
    val bookData = bookInfo.data?.volumeInfo
    val googleBookId = bookInfo.data?.id

    Column{
        Card(
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(6.dp)
        ){
            Image(
                painter = rememberImagePainter(data = bookData?.imageLinks?.thumbnail),
                contentDescription = "Book cover image",
                modifier = Modifier
                    .height(120.dp)
                    .padding(4.dp)
            )
        }

        Text(
            text = bookData?.title.toString(),
            style = MaterialTheme.typography.h6,
            overflow = TextOverflow.Ellipsis,
            maxLines = 10
        )
        Text(text = "Authors : `${bookData?.authors.toString()}")
        Text(text = "Page Count : `${bookData?.pageCount.toString()}")
        Text(text = "Categories : `${bookData?.categories.toString()}", style = MaterialTheme.typography.subtitle1, maxLines = 3, overflow = TextOverflow.Ellipsis)
        Text(text = "Published : `${bookData?.publishedDate.toString()}", style = MaterialTheme.typography.subtitle1)
        Spacer(modifier = Modifier.height(8.dp))

        val cleanedDescriptionText = HtmlCompat.fromHtml(bookData?.description.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY).toString()

        val localDims = LocalContext.current.resources.displayMetrics

        Surface(
            modifier = Modifier
                .height(localDims.heightPixels.dp.times(0.09f))
                .padding(4.dp),
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(1.dp, Color.DarkGray)
        ) {
            LazyColumn(){
                item{
                    Text(text = cleanedDescriptionText)
                }
            }
        }

        Row(
            modifier = Modifier
                .padding(16.dp)
                .height(48.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            RoundedButton(
                label = "Save"
            ){
                val book = MBook(
                    title = bookData?.title,
                    authors = bookData?.authors.toString(),
                    description = bookData?.description,
                    categories = bookData?.categories.toString(),
                    notes = "",
                    photoUrl = bookData?.imageLinks?.thumbnail.toString(),
                    publishedDate = bookData?.publishedDate,
                    pageCount = bookData?.pageCount.toString(),
                    rating = 0.0,
                    googleBookId = googleBookId,
                    userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
                )
                saveToFirebase(book, navController)
            }
            RoundedButton(
                label = "Cancel"
            ){
                navController.popBackStack()
            }
        }
    }

}

fun saveToFirebase(book: MBook,navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    val dbCollection = db.collection("books")

    if(book.toString().isNotEmpty()){
        dbCollection.add(book)
            .addOnSuccessListener { documentRef ->
                val docId = documentRef.id
                dbCollection.document(docId)
                    .update(hashMapOf("id" to docId) as HashMap<String, Any>)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            navController.popBackStack()
                        }
                    }
            }.addOnFailureListener {
                Log.d("Save Book to Firebase", "Error updating doc", it)
            }
    } else {

    }
}
