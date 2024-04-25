package com.samuelokello.trashtrack.ui.presentation.shared.auth

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samuelokello.trashtrack.R
import com.samuelokello.trashtrack.navigation.Screen
import com.samuelokello.trashtrack.ui.components.CustomButton
import com.samuelokello.trashtrack.ui.components.TextFieldComponent

@Composable
fun CreateProfileScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    val onCreateProfileClick = { navController.navigate(Screen.HOME.name) }

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
            text = "CREATE PROFILE",
            style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center),
            modifier = Modifier.padding(bottom = 20.dp)
        )
        TextFieldComponent(
            value = name,
            onValueChange = { name = it },
            placeholder = "Name",
            trailingIcon = {}
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldComponent(
            value = phone,
            onValueChange = { phone = it },
            placeholder = "Phone",
            trailingIcon = {}
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldComponent(
            value = location,
            onValueChange = { location = it },
            placeholder = "Location",
            trailingIcon = {}
        )
        Spacer(modifier = Modifier.height(32.dp))
        CustomButton(
            buttonText = "Create Profile",
            onClick = onCreateProfileClick
        )
    }
}


//@Composable
//fun CreateProfileScreen(
//    name: String,
//    onNameChange: (String) -> Unit,
//    phone: String,
//    onPhoneChange: (String) -> Unit,
//    location: String,
//    onLocationChange: (String) -> Unit,
//    onCreateProfileClick: () -> Unit
//) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(30.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.undraw_throw_away_re_x60k),
//            contentDescription = null,
//            modifier = Modifier
//                .size(150.dp)
//                .clip(CircleShape)
//                .padding(4.dp)
//                .border(4.dp, Color(0xFF4CAF50), CircleShape)
//        )
//
//        Spacer(modifier = Modifier.height(20.dp))
//        Text(
//            text = "CREATE ACCOUNT",
//            style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center),
//            modifier = Modifier.padding(bottom = 20.dp)
//        )
//        TextFieldComponent(
//            value = name,
//            onValueChange = onNameChange,
//            placeholder = "Name",
//            trailingIcon = {}
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        TextFieldComponent(
//            value = phone,
//            onValueChange = onPhoneChange,
//            placeholder = "Phone",
//            trailingIcon = {}
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        TextFieldComponent(
//            value = location,
//            onValueChange = onLocationChange,
//            placeholder = "Location",
//            trailingIcon = {}
//        )
//        Spacer(modifier = Modifier.height(32.dp))
//        CustomButton(
//            buttonText = "Create Profile",
//            onClick = onCreateProfileClick
//        )
//    }
//}