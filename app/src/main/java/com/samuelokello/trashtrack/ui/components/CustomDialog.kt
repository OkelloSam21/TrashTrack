package com.samuelokello.trashtrack.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samuelokello.trashtrack.R
import com.samuelokello.trashtrack.ui.theme.TrashTrackTheme

@Composable
fun CustomDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    description:String,
    buttonText: String,
    onButtonClick: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.icon),
                    contentDescription = "Icon",
                    modifier = Modifier.clip(CircleShape).size(30.dp)
                )
            },
            title = {
                Row {
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) { Text(text = "Trash Track") }
                }
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                }
            },
            confirmButton = {
                Row {
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Button(onClick = onButtonClick) { Text(text = buttonText) }
                    }
                }
            },
            dismissButton = { },
        )
    }
}

/**
 *     onDismissRequest: () -> Unit,
 *     confirmButton: @Composable () -> Unit,
 *     modifier: Modifier = Modifier,
 *     dismissButton: @Composable() (() -> Unit)? = null,
 *     icon: @Composable() (() -> Unit)? = null,
 *     title: @Composable() (() -> Unit)? = null,
 *     text: @Composable() (() -> Unit)? = null,
 *     shape: Shape = AlertDialogDefaults.shape,
 *     containerColor: Color = AlertDialogDefaults.containerColor,
 *     iconContentColor: Color = AlertDialogDefaults.iconContentColor,
 *     titleContentColor: Color = AlertDialogDefaults.titleContentColor,
 *     textContentColor: Color = AlertDialogDefaults.textContentColor,
 *     tonalElevation: Dp = AlertDialogDefaults.TonalElevation,
 *     properties: DialogProperties = DialogProperties()
 */

@Preview
@Composable
private fun DialogPreview() {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()

    ) {
        TrashTrackTheme {
            CustomDialog(
                showDialog = true,
                onDismissRequest = {},
                buttonText = "Button Text",
                description = "Dialog Description",
                onButtonClick = {}
            )
        }
    }
}