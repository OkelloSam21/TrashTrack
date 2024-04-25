package com.samuelokello.trashtrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.samuelokello.trashtrack.navigation.AppNavHost
import com.samuelokello.trashtrack.ui.theme.TrashTrackTheme

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        // Initialize Firebase Auth
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        setContent {
            TrashTrackTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Home()
                    AppNavHost(navController = rememberNavController())
                }
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
//            reload()
        }
    }
}
