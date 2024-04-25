package com.samuelokello.trashtrack.ui.presentation.shared.auth.sign_up

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samuelokello.trashtrack.R
import com.samuelokello.trashtrack.navigation.Screen
import com.samuelokello.trashtrack.ui.components.CustomButton
import com.samuelokello.trashtrack.ui.components.HandleLoading
import com.samuelokello.trashtrack.ui.components.PasswordFieldComponent
import com.samuelokello.trashtrack.ui.components.TextFieldComponent
import com.samuelokello.trashtrack.ui.theme.TrashTrackTheme

@Composable
fun SignUpScreen(navController: NavController) {
    Surface (
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ){
        TrashTrackTheme {
            val viewModel = SIgnUpVIewModel()
            SignUpScreenContent(
                viewModel = viewModel,
                event = viewModel::onEvent,
                navigateToSignIn = { navController.navigate(Screen.SIGN_IN.name) }
            )
        }
    }
}

@Composable
fun SignUpScreenContent(
    viewModel: SIgnUpVIewModel,
    event: (SignUpEvent) -> Unit,
    navigateToSignIn: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    var visibility by remember { mutableStateOf(false) }

    if (state.isLoading) HandleLoading()

    if (state.navigateToSignIn) navigateToSignIn()


    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Image(painter = painterResource(id = R.drawable.undraw_throw_away_re_x60k),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .border(4.dp, Color(0xFF4CAF50), CircleShape)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Sign Up", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(20.dp))

        TextFieldComponent(
            value = state.email,
            onValueChange = { event(SignUpEvent.EmailChanged(it)) },
            placeholder = "Email",
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null
                )
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        PasswordFieldComponent(
            value = state.password,
            onValueChange = { event(SignUpEvent.PasswordChanged(it)) },
            placeholder = "Password",
            isPassword = !visibility,
            trailingIcon = {
                IconButton(onClick = { visibility = !visibility }) {
                    Icon(
                        imageVector = if (visibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null
                    )
                }
            },
        )

        Spacer(modifier = Modifier.height(20.dp))

        CustomButton(
            buttonText = "Sign Up",
            onClick = {
                event(
                    SignUpEvent.SignUpClicked(
                        state.email,
                        state.password,
                        context
                    )
                )
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Already have an account?",
            style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center),
            modifier = Modifier.padding(top = 20.dp)
        )

        TextButton(
            onClick = { navigateToSignIn() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Sign In")
        }

    }

}

