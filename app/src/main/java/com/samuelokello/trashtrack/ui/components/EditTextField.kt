package com.samuelokello.trashtrack.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PasswordFieldComponent(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    trailingIcon: @Composable () -> Unit ={}
) {
    OutlinedTextField(
        value = value,
        onValueChange = { textFieldValue -> onValueChange(textFieldValue) },
        placeholder = { Text(placeholder, color = MaterialTheme.colorScheme.secondary) },
        modifier = modifier.fillMaxWidth(),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = trailingIcon
    )
}

@Composable
fun TextFieldComponent(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    trailingIcon: @Composable () -> Unit = {}
) {
    OutlinedTextField(
        value = value,
        onValueChange = { textFieldValue -> onValueChange(textFieldValue) },
        placeholder = { Text(placeholder, color = MaterialTheme.colorScheme.secondary) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        trailingIcon = trailingIcon,
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun PasswordFieldComponentPreview() {
    PasswordFieldComponent(
        value = "",
        onValueChange = {},
        placeholder = "Password",
        modifier = Modifier,
        isPassword = true
    )
}

@Preview(showBackground = true)
@Composable
fun TextFieldComponentPreview() {
    TextFieldComponent(
        value = "",
        onValueChange = {},
        placeholder = "Username",
        trailingIcon = {}
    )
}
