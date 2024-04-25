package com.samuelokello.trashtrack.ui.presentation.worker.request_pick_up

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samuelokello.trashtrack.ui.components.CustomButton
import com.samuelokello.trashtrack.ui.components.DatePicker
import com.samuelokello.trashtrack.ui.components.MultiSelectList
import com.samuelokello.trashtrack.ui.components.TextFieldComponent
import com.samuelokello.trashtrack.ui.theme.TrashTrackTheme

@Composable
fun RequestPickupScreen(navController: NavController) {
    var location by remember { mutableStateOf("") }
    val wasteType by remember {
        mutableStateOf(
            listOf(
                "General waste",
                "Biodegradable waste",
                "Recyclable waste",
                "Hazardous waste"
            )
        )
    }
    val selectedItems by remember {
        mutableStateOf(mutableStateOf( List(wasteType.size) { false }))
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Request Pickup", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "Select Date", style = MaterialTheme.typography.bodyMedium)
        TextFieldComponent(
            value = location,
            onValueChange = { location = it },
            placeholder = "Location",
            trailingIcon = {}
            )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Select Date", style = MaterialTheme.typography.bodyMedium)
        DatePicker()
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Select waste type", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        MultiSelectList(
            items = wasteType,
            selectedItems = selectedItems
        ) { index, isSelected ->
            selectedItems.value = selectedItems.value.toMutableList()
        }
        Spacer(modifier = Modifier.height(32.dp))
        CustomButton(buttonText = "Submit Request", onClick = { /* Handle submit request */ })
    }
}


@Preview(showBackground = true)
@Composable
private fun RequestPickUpPreview() {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        TrashTrackTheme {
        }
    }
}

