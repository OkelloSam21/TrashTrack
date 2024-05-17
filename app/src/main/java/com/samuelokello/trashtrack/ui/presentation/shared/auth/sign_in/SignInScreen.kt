package com.samuelokello.trashtrack.ui.presentation.shared.auth.sign_in

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samuelokello.trashtrack.R
import com.samuelokello.trashtrack.core.local.entity.User
import com.samuelokello.trashtrack.navigation.Screen
import com.samuelokello.trashtrack.ui.components.CustomButton
import com.samuelokello.trashtrack.ui.components.HandleLoading
import com.samuelokello.trashtrack.ui.components.PasswordFieldComponent
import com.samuelokello.trashtrack.ui.components.TextFieldComponent
import com.samuelokello.trashtrack.ui.theme.TrashTrackTheme

@Composable
fun SignInScreen(navController: NavController) {
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
                navigateToUserHome = { navController.navigate(Screen.HOME.name) },
                navigateToAdmin = { navController.navigate(Screen.ADMIN.name) }
            )
        }
    }
}

@Composable
fun SignInScreenContent(
    viewModel: SignInViewModel,
    event: (SignInEvent) -> Unit,
    navigateToSignUp: () -> Unit,
    navigateToUserHome: () -> Unit,
    navigateToAdmin: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    var visibility by remember { mutableStateOf(false) }

    if (state.isLoading) HandleLoading()

    LaunchedEffect(state.navigateToSignUp) {
        if (state.navigateToSignUp) navigateToSignUp()
    }

    LaunchedEffect(state.navigateToUserHome) {
        if (state.navigateToUserHome){

            navigateToUserHome()
        }
    }

    LaunchedEffect(state.navigateToAdminHome) {
        if (state.navigateToAdminHome) navigateToAdmin()
    }

    if (state.showForgotPasswordDialog) {
        ForgotPasswordDialog(
            showDialog = state.showForgotPasswordDialog,
            onDismissRequest = { event.invoke(SignInEvent.DismissDialog) },
            onSendEmail = { event.invoke(SignInEvent.SendEmail(it, context)) }
        )
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
            onValueChange = { email -> event(SignInEvent.EmailChanged(email)) },
            placeholder = "Email",
            trailingIcon = { Icon(Icons.Default.Email, contentDescription = "Email") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Sign-in password text field
        PasswordFieldComponent(
            value = state.password,
            onValueChange = { password -> event(SignInEvent.PasswordChanged(password)) },
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

        TextButton(
            onClick = {
                event(SignInEvent.ShowDialog)
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(
                text = "Forgot Password?",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 10.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))

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
                    )
                )
            },
            isEnabled = state.email.isNotEmpty() && state.password.isNotEmpty()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            // Text "Don't Have an account?"
            Text(
                text = "Don't Have an account?",
                style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center),
                modifier = Modifier.padding(top = 16.dp)
            )
            TextButton(
                onClick = {
                    // Handle Sign Up navigation
                    navigateToSignUp()
                },
                modifier = Modifier.padding(start = 2.dp, bottom = 16.dp)
            ) {
                Text("Sign Up", style = MaterialTheme.typography.bodyLarge)
            }
        }


        Spacer(modifier = Modifier.height(10.dp))

        // Sign-up button

    }
}

@Composable
fun ForgotPasswordDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    onSendEmail: (String) -> Unit,
) {
    if (showDialog) {
        var email by remember { mutableStateOf("") }  // Store email in mutable state

        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = { Text("Forgot Password") },
            text = {
                TextField(
                    value = email,  // Use the state variable
                    onValueChange = { email = it }, // Update state on change
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                CustomButton(
                    buttonText = "Send Email",
                    onClick = { onSendEmail(email) },
                    isEnabled = email.isNotEmpty() && email.contains("@")
                )
            },
            dismissButton = {
                CustomButton(
                    buttonText = "Cancel",
                    onClick = onDismissRequest,
                    isEnabled = true
                )
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun SignInPreview() {
//    SignInScreen()
}