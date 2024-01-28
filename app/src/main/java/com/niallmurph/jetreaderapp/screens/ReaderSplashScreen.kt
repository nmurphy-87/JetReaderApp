package com.niallmurph.jetreaderapp.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.niallmurph.jetreaderapp.components.ReaderLogo
import com.niallmurph.jetreaderapp.components.ReaderTitleCaption
import com.niallmurph.jetreaderapp.navigation.ReaderScreens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    val scale = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.9f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = {
                    OvershootInterpolator(8f)
                        .getInterpolation(it)
                }
            )
        )
        delay(1500L)
//        if(FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) {
//            navController.navigate(route = ReaderScreens.LoginScreen.name)
//        } else {
//            navController.navigate(route = ReaderScreens.HomeScreen.name)
//        }

        navController.navigate(route = ReaderScreens.LoginScreen.name)

    }

    Surface(
        modifier = Modifier
            .padding(16.dp)
            .size(320.dp)
            .scale(scale = scale.value),
        shape = CircleShape,
        color = Color.White,
        border = BorderStroke(
            2.dp, color =
            Color.LightGray
        )
    ) {
        Column(
            modifier = Modifier
                .padding(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ReaderLogo()
            Spacer(modifier = Modifier.height(16.dp))
            ReaderTitleCaption()
        }
    }
}
