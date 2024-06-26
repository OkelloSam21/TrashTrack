package com.samuelokello.trashtrack.ui.presentation.shared.auth.create_profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.samuelokello.trashtrack.R
import com.samuelokello.trashtrack.navigation.Screen
import com.samuelokello.trashtrack.ui.components.CustomButton
import com.samuelokello.trashtrack.ui.components.HandleLoading
import com.samuelokello.trashtrack.ui.components.LocationAutocompleteTextField
import com.samuelokello.trashtrack.ui.components.TextFieldComponent
import com.samuelokello.trashtrack.ui.theme.TrashTrackTheme

@Composable
fun CreateProfileScreen(navController: NavController) {
    val  viewModel: CreateProfileViewModel = hiltViewModel()
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        TrashTrackTheme {
            CreateProfileScreenContent(
                state = viewModel.state.collectAsState().value,
                event = viewModel::onEvent,
                navigateToSignIn = { navController.navigate(Screen.SIGN_IN.name) }
            )
        }
    }

}
@Composable
fun CreateProfileScreenContent(
    state: CreateProfileUiState,
    event: (CreateProfileEvent) -> Unit,
    navigateToSignIn: () -> Unit,
) {
    val context = LocalContext.current

    if (state.isLoading) HandleLoading()

    LaunchedEffect(state.navigateToSignIn) {
        if (state.navigateToSignIn) {
            navigateToSignIn()
        }
    }

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
                .border(4.dp, Color(0xFF4CAF50), CircleShape)
        )

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "CREATE PROFILE",
            style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center),
            modifier = Modifier.padding(bottom = 20.dp)
        )
        TextFieldComponent(
            value = state.name,
            onValueChange = { event(CreateProfileEvent.NameChanged(it)) },
            placeholder = "Name",
            trailingIcon = {
                Icon(Icons.Default.Person, contentDescription = null)
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldComponent(
            value = state.phone,
            onValueChange = { event(CreateProfileEvent.PhoneChanged(it)) },
            placeholder = "Phone",
            trailingIcon = {
                Icon(Icons.Default.Phone, contentDescription = null)
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        LocationAutocompleteTextField(
            value = state.location,
            onValueChange = {
                event(CreateProfileEvent.LocationChanged(it))
                            },
            suggestions = state.autoCompleteSuggestions,
            onSuggestionSelected = {
                               event(CreateProfileEvent.LocationChanged(it.getFullText(null).toString()))
            },
            placeholder = "Location"
        )
        Spacer(modifier = Modifier.height(32.dp))
        CustomButton(
            buttonText = "Create Profile",
            onClick = { event(CreateProfileEvent.CreateProfileClicked(context)) },
            isEnabled = state.name.isNotEmpty() && state.phone.isNotEmpty() && state.location.isNotEmpty()
        )
    }
}
