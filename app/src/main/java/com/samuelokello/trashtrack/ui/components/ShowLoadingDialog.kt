package com.samuelokello.trashtrack.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun ShowLoadingDialog() {
    Dialog(
        onDismissRequest = {},
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Surface {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(48.dp)
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(8.dp)
                        )
                )
            }
        }
    }
}