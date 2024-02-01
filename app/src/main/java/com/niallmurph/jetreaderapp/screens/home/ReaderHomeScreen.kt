package com.niallmurph.jetreaderapp.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.niallmurph.jetreaderapp.components.*
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
        ListCard()
    }
}

@Composable
fun ReadingRightNowArea(
    books: List<MBook>, navController: NavController
) {

}

