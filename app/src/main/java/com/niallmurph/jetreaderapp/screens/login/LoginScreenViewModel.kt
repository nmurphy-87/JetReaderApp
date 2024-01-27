package com.niallmurph.jetreaderapp.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginScreenViewModel : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun signInWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {

        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                    } else {
                        Log.d(
                            "LoginScreenViewModel",
                            "Sign in not successful : ${task.result.toString()}"
                        )
                    }
                }
        } catch (e: Exception) {
            Log.d(
                "LoginScreenViewModel",
                "Exception when attempting sign in : ${e.message}"
            )
        }
    }

    fun createUerWithEmailAndPassword(email: String, password: String) {

    }

}