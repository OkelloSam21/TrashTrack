package com.samuelokello.trashtrack.ui.presentation.shared.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.samuelokello.trashtrack.R
import com.samuelokello.trashtrack.navigation.Screen
import com.samuelokello.trashtrack.ui.components.CustomButton

@Composable
fun WelcomeScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Circle ImageView (You may use Coil or other libraries for image loading)
        Image(
            painter = painterResource(R.drawable.undraw_throw_away_re_x60k),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .border(4.dp, Color(0xFF4CAF50), CircleShape)
        )

        Spacer(modifier = Modifier.height(30.dp))

        // TextView (Welcome text)
        Text(
            text = "Welcome",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 20.dp)
        )
        CustomButton(buttonText = stringResource(id = R.string.signUp), onClick = { navController.navigate(Screen.SIGNUP.name) })

        Spacer(modifier = Modifier.height(20.dp))

        // Sign In Button
        CustomButton(buttonText = stringResource(id = R.string.sign_in), onClick = { navController.navigate(Screen.SIGN_IN.name) })
    }
}

