package com.niallmurph.jetreaderapp.screens.login

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.niallmurph.jetreaderapp.components.EmailInputTextField
import com.niallmurph.jetreaderapp.components.PasswordInputTextField
import com.niallmurph.jetreaderapp.components.ReaderLogo
import com.niallmurph.jetreaderapp.components.SubmitButton
import com.niallmurph.jetreaderapp.R
import com.niallmurph.jetreaderapp.navigation.ReaderScreens

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val showLoginForm = rememberSaveable {
        mutableStateOf(true)
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            ReaderLogo()
            if(showLoginForm.value){
                UserForm(
                    loading = false,
                    isCreateAccount = false
                ) { email, pwd ->
                    Log.d("LOGIN FORM", "ReaderLoginScreen: $email $pwd")
                    //TODO : Firebase Login
                    viewModel.signInWithEmailAndPassword(
                        email = email,
                        password = pwd
                    ){
                        navController.navigate(route = ReaderScreens.HomeScreen.name)
                    }
                }
            } else {
                UserForm(
                    loading = false,
                    isCreateAccount = true
                ) { email, pwd ->
                    Log.d("SIGNUP FORM", "ReaderLoginScreen: $email $pwd")
                    viewModel.createUerWithEmailAndPassword(
                        email = email,
                        password = pwd
                    ){
                        navController.navigate(route = ReaderScreens.HomeScreen.name)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .padding(12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {


                val prefixText = if(showLoginForm.value) "New User?" else "Existing User?"
                val suffixLinkText = if(showLoginForm.value) "Sign Up" else "Login"

                Text(text = prefixText)
                Text(
                    text = suffixLinkText,
                    modifier = Modifier
                        .clickable {
                            showLoginForm.value = !showLoginForm.value
                        }
                        .padding(4.dp),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.secondaryVariant

                )

            }
        }
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun UserForm(
    loading: Boolean = false,
    isCreateAccount: Boolean = false,
    onDone: (String, String) -> Unit = { email, pwd -> }
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val isValid = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }

    val modifier = Modifier
        .height(360.dp)
        .background(MaterialTheme.colors.background)
        .verticalScroll(rememberScrollState())

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(isCreateAccount) Text(
            text = stringResource(id = R.string.create_acct),
            modifier = Modifier
                .padding(6.dp)
        )
        EmailInputTextField(
            emailState = email,
            enabled = !loading,
            onAction = KeyboardActions {
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
                if (!isValid) return@KeyboardActions
                onDone(email.value.trim(), password.value.trim())
            }
        )

        SubmitButton(
            textId = if (isCreateAccount) "Create Account" else "Login",
            loading = loading,
            validInputs = isValid,
        ) {
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()
        }
    }
}
