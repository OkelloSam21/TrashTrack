package com.samuelokello.trashtrack.ui.presentation.admin.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samuelokello.trashtrack.R
import com.samuelokello.trashtrack.navigation.Screen
import com.samuelokello.trashtrack.ui.components.WasteReportCard
@Composable
fun AdminHomeScreen(navController: NavController) {
    AdminHomeScreenContent(
        navigateToReports = { navController.navigate(Screen.REPORTED_WASTE.name)},
        navigateToPickups = { navController.navigate(Screen.PICK_UP_REQUEST.name)},
        viewModel = AdminViewModel()
    )
}
@Composable
fun AdminHomeScreenContent(
    navigateToReports: () -> Unit,
    navigateToPickups: () -> Unit,
    viewModel: AdminViewModel
) {
    val reports = viewModel.reportsCount.collectAsState()
    val requests = viewModel.requestsCount.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.undraw_throw_away_re_x60k),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .padding(4.dp)
                .border(4.dp, Color(0xFF4CAF50), CircleShape)
        )

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Click To View",
            style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center),
            modifier = Modifier.padding(bottom = 20.dp)
        )
        WasteReportCard(
            buttonText = " Pick Ups Requested",
            badgeCount = requests.value,
            onButtonClick = { navigateToPickups()}
        )
        Spacer(modifier = Modifier.height(16.dp))
        WasteReportCard(
            buttonText = "Reported Waste",
            badgeCount = reports.value,
            onButtonClick = { navigateToReports() }
        )
    }
}