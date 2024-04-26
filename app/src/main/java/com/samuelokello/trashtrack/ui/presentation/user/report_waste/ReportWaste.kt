package com.samuelokello.trashtrack.ui.presentation.user.report_waste

import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.samuelokello.trashtrack.ui.components.CustomButton
import com.samuelokello.trashtrack.ui.components.DatePicker
import com.samuelokello.trashtrack.ui.components.HandleLoading
import com.samuelokello.trashtrack.ui.components.MultiSelectList
import com.samuelokello.trashtrack.ui.components.TextFieldComponent
import com.samuelokello.trashtrack.ui.presentation.worker.report_waste.ReportEvent
import com.samuelokello.trashtrack.ui.presentation.worker.report_waste.ReportViewModel
import com.samuelokello.trashtrack.ui.theme.TrashTrackTheme

@Composable
fun ReportWasteScreen(navController: NavController) {
    Surface(modifier = Modifier.fillMaxSize()) {
        TrashTrackTheme {
            val viewModel: ReportViewModel = viewModel()
            ReportWasteScreenContent(
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
fun ReportWasteScreenContent(
    viewModel: ReportViewModel,
    event:(ReportEvent) -> Unit,
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
        mutableStateOf(mutableStateOf(List(wasteType.size) { false }))
    }

    if (state.isLoading) HandleLoading()

    LaunchedEffect(state.navigateToHome) {
        if (state.navigateToHome) navigateBack()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Report Waste", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "Location", style = MaterialTheme.typography.bodyMedium)
        TextFieldComponent(
            value = state.wasteLocation,
            onValueChange = { event(ReportEvent.WasteLocationChanged(it)) },
            placeholder = "Location",
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location"
                )
            }
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
        CustomButton(
            buttonText = "Report Waste",
            onClick = { event(ReportEvent.OnSubmitClicked(
                state.wasteLocation,
                state.wasteType,
                state.date,
                context = context,
            )
            ) }
        )
    }
}