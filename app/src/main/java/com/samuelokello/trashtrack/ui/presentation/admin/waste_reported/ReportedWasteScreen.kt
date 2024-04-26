package com.samuelokello.trashtrack.ui.presentation.admin.waste_reported

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samuelokello.trashtrack.ui.components.CustomButton
import com.samuelokello.trashtrack.ui.theme.TrashTrackTheme

@Composable
fun ReportedWasteScreen(navController: NavController) {
    Surface {
        TrashTrackTheme {
            val viewModel = ReportedWasteViewModel()
            ReportedWasteScreenContent(
                viewModel = viewModel
            )

        }
    }
}



@Composable
fun ReportedWasteScreenContent(viewModel: ReportedWasteViewModel) {
    val reports = viewModel.reports.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value

    if (isLoading) {
        // Display a loading indicator
        CircularProgressIndicator()
    } else {
        LazyColumn {
            items(reports) { report ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = "Report ID: ${report.id}")
                        Text(text = "Report Details: type: ${report.wasteType} date reported ${report.date} ")
                        CustomButton(
                            buttonText = "Done",
                            onClick = { /* Handle report clearing here */ }
                        )
                    }
                }
            }
        }
    }
}
