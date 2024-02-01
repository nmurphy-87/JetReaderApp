package com.niallmurph.jetreaderapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.niallmurph.jetreaderapp.navigation.ReaderScreens

@Composable
fun ReaderLogo(modifier: Modifier = Modifier) {
    Text(
        text = "Jet Reader",
        modifier = modifier.padding(bottom = 16.dp),
        style = MaterialTheme.typography.h3,
        color = Color.Red.copy(alpha = 0.5f)
    )
}

@Composable
fun ReaderTitleCaption(modifier: Modifier = Modifier) {
    Text(
        text = "\"Read, Change Yourself \"",
        modifier = modifier,
        style = MaterialTheme.typography.h5,
        color = Color.Gray
    )
}

@Composable
fun TitleSection(
    modifier: Modifier = Modifier,
    label: String
) {
    Surface(
        modifier = modifier
            .padding(6.dp)
    ) {
        Column {
            Text(
                text = label,
                fontSize = 20.sp,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Composable
fun ReaderAppBar(
    title: String,
    icon: ImageVector? = null,
    showProfile: Boolean = true,
    navController: NavController,
    onBackArrowClicked: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (showProfile) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Logo Icon",
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .scale(0.6f)
                    )
                }
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Back Arrow Icon",
                        tint = Color.Red.copy(0.7f),
                        modifier = Modifier
                            .clickable {
                                onBackArrowClicked.invoke()
                            }
                    )
                }
                Spacer(modifier = Modifier.width(40.dp))
                Text(
                    text = title,
                    color = Color.Red.copy(alpha = 0.7f),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                )
                Spacer(modifier = Modifier.weight(0.1f))

            }
        },
        actions = {
            if (showProfile) {
                IconButton(
                    onClick = {
                        FirebaseAuth.getInstance().signOut().run {
                            navController.navigate(ReaderScreens.LoginScreen.name)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = "Logout",
                        tint = Color.Green.copy(alpha = 0.5f)
                    )
                }
            }
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    )
}