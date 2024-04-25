package com.samuelokello.trashtrack.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.samuelokello.trashtrack.ui.presentation.shared.auth.create_profile.CreateProfileScreen
import com.samuelokello.trashtrack.ui.presentation.shared.auth.sign_in.SignInScreen
import com.samuelokello.trashtrack.ui.presentation.shared.auth.sign_up.SignUpScreen
import com.samuelokello.trashtrack.ui.presentation.shared.welcome.WelcomeScreen
import com.samuelokello.trashtrack.ui.presentation.worker.Home
import com.samuelokello.trashtrack.ui.presentation.worker.report_waste.ReportWaste
import com.samuelokello.trashtrack.ui.presentation.worker.request_pick_up.RequestPickupScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Welcome.route,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Welcome.route) {
            WelcomeScreen(navController)
        }
        composable(NavigationItem.Signup.route) {
            SignUpScreen(navController )
        }
        composable(NavigationItem.signin.route) {
            SignInScreen(navController)
        }
        composable(NavigationItem.CreateProfile.route) {
            CreateProfileScreen(navController)
        }
        composable(NavigationItem.CreateProfile.route) {
             CreateProfileScreen(navController = navController )
        }
        composable(NavigationItem.Home.route) {
            Home(navController)
        }
        composable(NavigationItem.Request.route) {
            RequestPickupScreen(navController = navController)
        }
        composable(NavigationItem.Report.route) {
            ReportWaste(navController = navController)
        }
    }
}
