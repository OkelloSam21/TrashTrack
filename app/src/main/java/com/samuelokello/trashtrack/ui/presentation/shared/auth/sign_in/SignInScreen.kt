package com.samuelokello.trashtrack.ui.presentation.shared.auth.sign_in

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.samuelokello.trashtrack.R
import com.samuelokello.trashtrack.data.local.User
import com.samuelokello.trashtrack.navigation.Screen
import com.samuelokello.trashtrack.ui.components.CustomButton
import com.samuelokello.trashtrack.ui.components.HandleLoading
import com.samuelokello.trashtrack.ui.components.PasswordFieldComponent
import com.samuelokello.trashtrack.ui.components.TextFieldComponent
import com.samuelokello.trashtrack.ui.theme.TrashTrackTheme

@Composable
fun SignInScreen(navController: NavController,) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        TrashTrackTheme {
            val viewModel = SignInViewModel()
            SignInScreenContent(
                viewModel = viewModel,
                event = viewModel::onEvent,
                navigateToSignUp = { navController.popBackStack() },
                navigateToCreateProfile = { navController.navigate(Screen.CREATE_PROFILE.name)},
                navigateToAdmin = { navController.navigate(Screen.ADMIN.name) }
            )
        }
    }
}

@Composable
fun SignInScreenContent(
    viewModel: SignInViewModel,
    event:(SignInEvent) -> Unit,
    navigateToSignUp: () -> Unit,
    navigateToCreateProfile:() -> Unit,
    navigateToAdmin:() -> Unit
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    var visibility by remember { mutableStateOf(false) }

    if (state.isLoading) HandleLoading()

    LaunchedEffect( state.navigateToSignUp) {
        if (state.navigateToSignUp) navigateToSignUp()
    }

    LaunchedEffect (state.navigateToUserHome){
        if (state.navigateToUserHome) navigateToCreateProfile()
    }
    LaunchedEffect(state.navigateToAdminHome){
        if (state.navigateToAdminHome) navigateToAdmin()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Image at the top
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

        // Text "SIGN IN"
        Text(
            text = "SIGN IN",
            style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center),
            modifier = Modifier.padding(bottom = 20.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Sign-in email text field
        TextFieldComponent(
            value = state.email,
            onValueChange = {email -> event(SignInEvent.EmailChanged(email))},
            placeholder = "Email",
            trailingIcon = { Icon(Icons.Default.Email, contentDescription = "Email" ) },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Sign-in password text field
        PasswordFieldComponent(
            value = state.password,
            onValueChange = { password-> event(SignInEvent.PasswordChanged(password)) },
            placeholder = "Password",
            isPassword = !visibility,
            trailingIcon = {
                IconButton(
                    onClick = { visibility = !visibility },
                    modifier = Modifier.clip(MaterialTheme.shapes.medium)
                ) {
                    if (visibility)
                        Icon(Icons.Default.Visibility, contentDescription = null)

                    else
                        Icon(Icons.Default.VisibilityOff, contentDescription = null)
                }
            }
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Sign-in button
        CustomButton(
            buttonText = "Sign In",
            onClick = {
                val user = User(email = state.email)
                event(
                    SignInEvent.SignInClicked(
                    state.email,
                        state.password,
                        context,
                        user = user,
                ))
            },
            isEnabled = state.email.isNotEmpty() && state.password.isNotEmpty()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Text "Don't Have an account?"
        Text(
            text = "Don't Have an account?",
            style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center),
            modifier = Modifier.padding(top = 10.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Sign-up button
        TextButton(
            onClick = {
                // Handle Sign Up navigation
               navigateToSignUp()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Sign Up")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignInPreview() {
//    SignInScreen()
}