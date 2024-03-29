package com.niallmurph.jetreaderapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.firestore.FirebaseFirestore
import com.niallmurph.jetreaderapp.navigation.ReaderNavigation
import com.niallmurph.jetreaderapp.ui.theme.JetReaderAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetReaderAppTheme {

                ReaderApp()

//                val db = FirebaseFirestore.getInstance()
//                val user : MutableMap<String, Any> = HashMap()
//                user["firstName"] = "Joe"
//                user["lastName"] = "Bloggs"
//
//                db.collection("users")
//                    .add(user)
//                    .addOnSuccessListener {
//                        Log.d("FB", "onCreate: ${it.id}")
//                    }.addOnFailureListener {
//                        Log.d("FB", "onCreate: $it")
//                    }
                // A surface container using the 'background' color from the theme
            }
        }
    }
}

@Composable
fun ReaderApp() {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ReaderNavigation()
        }
    }
}