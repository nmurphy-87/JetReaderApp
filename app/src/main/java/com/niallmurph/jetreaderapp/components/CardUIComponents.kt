package com.niallmurph.jetreaderapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.niallmurph.jetreaderapp.models.MBook

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
                    painter = rememberImagePainter(data = "https://m.media-amazon.com/images/I/71Gt0U59D3L._AC_UF894,1000_QL80_.jpg"),
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
                    BookRatingInset(score = 3.5)
                }
            }
            Text(
                text = book.title.toString(),
                modifier = Modifier
                    .padding(4.dp),
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = book.authors.toString(),
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