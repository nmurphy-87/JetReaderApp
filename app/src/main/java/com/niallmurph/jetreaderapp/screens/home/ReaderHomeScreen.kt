package com.niallmurph.jetreaderapp.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.niallmurph.jetreaderapp.components.FABContent
import com.niallmurph.jetreaderapp.components.ReaderAppBar
import com.niallmurph.jetreaderapp.components.TitleSection
import com.niallmurph.jetreaderapp.models.MBook
import com.niallmurph.jetreaderapp.navigation.ReaderScreens

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            ReaderAppBar(title = "JetReader", navController = navController)
        },
        floatingActionButton = {
            FABContent {}
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            HomeContent(
                navController = navController
            )
        }
    }
}

@Preview
@Composable
fun HomeContent(
    navController: NavController = NavController(LocalContext.current)
) {
    val currentUser = if (!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) {
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
                    text = currentUser!!,
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
    }
}

@Composable
fun ReadingRightNowArea(
    books: List<MBook>, navController: NavController
) {

}

@Composable
fun RoundedButton(
    label : String = "Reading",
    radius : Int = 26,
    onPress : () -> Unit = {}
){
    Surface(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    bottomEndPercent = radius,
                    topStartPercent = radius
                )
            ),
        color = Color(0xFF92CBDF)
    ) {
        Column(
            modifier = Modifier
                .width(96.dp)
                .fillMaxHeight()
                .clickable {
                    onPress.invoke()
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp
                )
            )
        }
    }
}

@Preview
@Composable
fun ListCard(
    book: MBook = MBook(
        id = "123",
        title = "Lorem  Ipsum",
        authors = "Me",
        notes = "Lorem Ipsum etc etc..."
    ),
    onPressDetails: (String) -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(28.dp),
        backgroundColor = Color.White,
        elevation = 6.dp,
        modifier = Modifier
            .padding(16.dp)
            .height(240.dp)
            .width(200.dp)
            .clickable {
                onPressDetails.invoke(book.title.toString())
            }
    ) {

        val context = LocalContext.current
        val resources = context.resources
        val displayMetrics = resources.displayMetrics

        val screenWidth = displayMetrics.widthPixels / displayMetrics.density
        val spacing = 12.dp

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(screenWidth.dp - (spacing * 2)),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = rememberImagePainter(data = ""),
                    contentDescription = "",
                    modifier = Modifier
                        .height(140.dp)
                        .width(100.dp)
                        .padding(4.dp)
                )
                Spacer(modifier = Modifier.width(48.dp))
                Column(
                    modifier = Modifier
                        .padding(top = 24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Fav icon",
                        modifier = Modifier
                            .padding(1.dp)
                    )
                    BookRating(score = 3.5)
                }
            }
            Text(
                text = "Book Title",
                modifier = Modifier
                    .padding(4.dp),
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Authors",
                modifier = Modifier
                    .padding(4.dp),
                style = MaterialTheme.typography.caption
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                RoundedButton(
                    label = "Reading",
                    radius = 60
                )
            }
        }

    }
}

@Composable
fun BookRating(score: Double = 4.5) {
    Surface(
        modifier = Modifier
            .height(72.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(54.dp),
        elevation = 6.dp,
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.StarBorder,
                contentDescription = "Star Icon",
                modifier = Modifier
                    .padding(2.dp)
            )
            Text(
                text = score.toString(),
                style = MaterialTheme.typography.subtitle1
            )
        }

    }
}

