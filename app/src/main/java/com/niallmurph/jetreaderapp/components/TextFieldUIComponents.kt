package com.niallmurph.jetreaderapp.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmailInputTextField(
    modifier: Modifier = Modifier,
    emailState: MutableState<String>,
    label: String = "Email",
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    InputField(
        modifier = modifier,
        valueState = emailState,
        labelId = label,
        enabled = enabled,
        imeAction = imeAction,
        onAction = onAction
    )
}

@Composable
fun PasswordInputTextField(
    modifier: Modifier = Modifier,
    passwordState: MutableState<String>,
    label: String = "Password",
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
    passwordVisibility: MutableState<Boolean>,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    InputField(
        modifier = modifier,
        valueState = passwordState,
        labelId = label,
        enabled = enabled,
        visibility = passwordVisibility,
        keyboardType = KeyboardType.Password,
        imeAction = imeAction,
        isPasswordField = true,
        onAction = onAction
    )
}

@Composable
private fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean = true,
    isSingleLine: Boolean = true,
    visibility: MutableState<Boolean> = mutableStateOf(true),
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    isPasswordField: Boolean = false,
    onAction: KeyboardActions = KeyboardActions.Default
) {

    val visualTransformation =
        if (visibility.value) VisualTransformation.None else PasswordVisualTransformation()

    OutlinedTextField(
        value = valueState.value,
        onValueChange = {
            valueState.value = it
        },
        label = {
            Text(labelId)
        },
        textStyle = TextStyle(
            fontSize = 16.sp,
            color = MaterialTheme.colors.onBackground
        ),
        modifier = modifier
            .padding(bottom = 12.dp, start = 12.dp, end = 12.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        visualTransformation = visualTransformation,
        trailingIcon = {
            if (isPasswordField) {
                PasswordVisibility(passwordVisibility = visibility)
            }
        },
        keyboardActions = onAction,
        singleLine = isSingleLine
    )
}

@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {
    val visible = passwordVisibility.value
    IconButton(
        onClick = {
            passwordVisibility.value = !visible
        }
    ) {
        Icons.Default.Close
    }
}
