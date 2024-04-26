package com.samuelokello.trashtrack.ui.presentation.user.request_pick_up

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.samuelokello.trashtrack.ui.components.CustomButton
import com.samuelokello.trashtrack.ui.components.DatePicker
import com.samuelokello.trashtrack.ui.components.HandleLoading
import com.samuelokello.trashtrack.ui.components.MultiSelectList
import com.samuelokello.trashtrack.ui.components.TextFieldComponent
import com.samuelokello.trashtrack.ui.theme.TrashTrackTheme

@Composable
fun RequestPickupScreen(navController: NavController) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        TrashTrackTheme {
            val viewModel: RequestViewModel = viewModel()
            RequestPickupScreenContent(
                viewModel = viewModel,
                event = { event ->
                    viewModel.onEvent(event)
                },
                navigateBack = { navController.popBackStack() }
            )

        }
    }

}
@Composable
fun RequestPickupScreenContent(
    viewModel: RequestViewModel,
    event:(RequestEvent) -> Unit,
    navigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

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

    if (state.isLoading) HandleLoading()

    LaunchedEffect(state.navigateToHome) {
        if (state.navigateToHome) navigateBack()
    }
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Request Pickup", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "Select Date", style = MaterialTheme.typography.bodyMedium)
        TextFieldComponent(
            value = state.wasteLocation,
            onValueChange = { event(RequestEvent.WasteLocationChanged(it)) },
            placeholder = "Location",
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location"
                )            }
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
        CustomButton(buttonText = "Submit Request", onClick = {
            event(RequestEvent.OnSubmitClicked(
                wasteType = wasteType[selectedItems.value.indexOf(true)],
                wasteLocation = state.wasteLocation,
                date = state.date,
                context
            ))
        })
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

