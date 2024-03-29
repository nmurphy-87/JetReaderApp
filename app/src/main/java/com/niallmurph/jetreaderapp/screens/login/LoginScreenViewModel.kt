package com.niallmurph.jetreaderapp.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.niallmurph.jetreaderapp.models.AppUserModel
import kotlinx.coroutines.launch

class LoginScreenViewModel : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun signInWithEmailAndPassword(email: String, password: String, navigateToHome: () -> Unit) =
        viewModelScope.launch {

            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(
                                "LoginScreenViewModel",
                                "Sign in successful : ${task.result.toString()}"
                            )
                            navigateToHome.invoke()
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

    fun createUerWithEmailAndPassword(email: String, password: String, navigateToHome: () -> Unit) {
        if (_loading.value == false) {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val displayName  = task.result?.user?.email?.split("@")?.get(0)
                        createUser(displayName)
                        Log.d(
                            "LoginScreenViewModel",
                            "Sign up successful :) : ${task.result.toString()}"
                        )
                        navigateToHome.invoke()
                    } else {
                        Log.d(
                            "LoginScreenViewModel",
                            "Sign up not successful : ${task.result.toString()}"
                        )
                    }
                    _loading.value = false
                }
        }
    }

    private fun createUser(displayName: String?) {
        val userId = auth.currentUser?.uid
        val user = AppUserModel(
            userId = userId!!,
            displayName = displayName!!,
            avatarUrl = "test.com",
            quote = "quote",
            profession = "tester",
            id = null
        ).toMap()

        FirebaseFirestore.getInstance().collection("users")
            .add(user)
    }

}