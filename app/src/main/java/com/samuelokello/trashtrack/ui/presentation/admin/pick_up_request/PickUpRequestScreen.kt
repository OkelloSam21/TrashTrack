package com.samuelokello.trashtrack.ui.presentation.admin.pick_up_request

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samuelokello.trashtrack.ui.components.CustomButton
import com.samuelokello.trashtrack.ui.theme.TrashTrackTheme

@Composable
fun PickUpRequestScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.background
    ){
        TrashTrackTheme {
            val viewModel = PickUpRequestViewModel()
            PickUpRequestScreenContent(
                viewModel = viewModel
            )
        }
    }
}
@Composable
fun PickUpRequestScreenContent(viewModel: PickUpRequestViewModel) {
    val requests by viewModel.requests.collectAsState()

    LazyColumn {
        items(requests) { request ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Request ID: ${request.id}")
                    Text(text = "Request Details: ${request.details}")
                    CustomButton(
                        buttonText = "Done",
                        onClick = {  }
                    )
                }
            }
        }
    }
}