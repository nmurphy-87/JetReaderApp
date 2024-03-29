package com.niallmurph.jetreaderapp.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BookRatingInset(score: Double = 4.5) {
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