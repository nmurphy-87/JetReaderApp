package com.niallmurph.jetreaderapp.screens.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.niallmurph.jetreaderapp.components.EmailInputTextField
import com.niallmurph.jetreaderapp.components.PasswordInputTextField
import com.niallmurph.jetreaderapp.components.ReaderLogo
import com.niallmurph.jetreaderapp.components.SubmitButton

@Composable
fun LoginScreen(navController: NavController) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ){
            ReaderLogo()
            UserForm(
                loading = false,
                isCreateAccount = false
            ) { email, pwd ->
                Log.d("FORM", "ReaderLoginScreen: $email $pwd")
            }
        }
    }

}
@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun UserForm(
    loading : Boolean = false,
    isCreateAccount : Boolean = false,
    onDone : (String, String) -> Unit = {email, pwd ->}
){
    val email = rememberSaveable{ mutableStateOf("") }
    val password = rememberSaveable{ mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val isValid = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }

    val modifier = Modifier
        .height(240.dp)
        .background(MaterialTheme.colors.background)
        .verticalScroll(rememberScrollState())

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailInputTextField(
            emailState = email,
            enabled = !loading,
            onAction = KeyboardActions{
                passwordFocusRequest.requestFocus()
            }
        )
        PasswordInputTextField(
            modifier = Modifier
                .focusRequester(passwordFocusRequest),
            passwordState = password,
            enabled = !loading,
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions {
                if(!isValid) return@KeyboardActions
                onDone(email.value.trim(), password.value.trim())
            }
        )

        SubmitButton(
            textId = if(isCreateAccount) "Create Account" else "Login",
            loading = loading,
            validInputs = isValid,
        ) {
            onDone(email.value.trim(), password.value.trim())
        }
    }
}
