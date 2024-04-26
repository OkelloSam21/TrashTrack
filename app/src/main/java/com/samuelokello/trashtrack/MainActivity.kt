package com.samuelokello.trashtrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.samuelokello.trashtrack.data.local.AppDatabase
import com.samuelokello.trashtrack.data.repository.RoomUserRepository
import com.samuelokello.trashtrack.navigation.AppNavHost
import com.samuelokello.trashtrack.navigation.AppViewModel
import com.samuelokello.trashtrack.navigation.NavigationItem
import com.samuelokello.trashtrack.ui.theme.TrashTrackTheme
import kotlinx.coroutines.launch

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
                    val navController = rememberNavController()
                    val coroutineScope = rememberCoroutineScope()
                    val context = LocalContext.current
                    val db = AppDatabase.getDatabase(context)
                    val userDao = db.userDao()
                    val repository = RoomUserRepository(userDao = userDao)
                    val viewModel = AppViewModel(repository)

                    coroutineScope.launch {
                        val user = db.userDao().getUser()
                        if (user != null) {
                            // User and profile exist in the database, navigate to Home screen
                            navController.navigate(NavigationItem.Home.route)
                        } else {
                            // User or profile does not exist in the database, navigate to Welcome screen
                            navController.navigate(NavigationItem.Welcome.route)
                        }
                    }

                    AppNavHost(navController = navController)
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

//    override fun onCreate(savedInstanceState: Bundle?) {
//        // Initialize Firebase Auth
//        auth = Firebase.auth
//        super.onCreate(savedInstanceState)
//
//        setContent {
//            TrashTrackTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
////                    Home()
//                    AppNavHost(navController = rememberNavController())
//                }
//            }
//        }
//    }



