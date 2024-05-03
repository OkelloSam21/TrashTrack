package com.samuelokello.trashtrack.ui.components

import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samuelokello.trashtrack.ui.theme.seed

@Composable
fun CustomButton(
    buttonText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    isEnabled: Boolean = false
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = seed,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = buttonText,
            color = textColor,
            style = textStyle
        )
    }
}

@Preview
@Composable
private fun ButtonPreview() {
    CustomButton(
        buttonText = "Sign Up",
        onClick = { /* Handle Sign Up */ },
        modifier = Modifier.fillMaxWidth()
    )
}
