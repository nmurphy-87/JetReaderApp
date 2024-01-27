package com.niallmurph.jetreaderapp.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmailInputTextField(
    modifier : Modifier = Modifier,
    emailState : MutableState<String>,
    label : String = "Email",
    enabled : Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction : KeyboardActions = KeyboardActions.Default
){
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
private fun InputField(
    modifier : Modifier = Modifier,
    valueState : MutableState<String>,
    labelId : String,
    enabled : Boolean = true,
    isSingleLine : Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction : KeyboardActions = KeyboardActions.Default
) {
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
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction)
    )
}
