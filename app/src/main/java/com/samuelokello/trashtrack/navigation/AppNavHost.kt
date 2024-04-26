package com.samuelokello.trashtrack.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.samuelokello.trashtrack.data.local.AppDatabase
import com.samuelokello.trashtrack.data.repository.RoomUserRepository
import com.samuelokello.trashtrack.ui.presentation.admin.home.AdminHomeScreen
import com.samuelokello.trashtrack.ui.presentation.admin.pick_up_request.PickUpRequestScreen
import com.samuelokello.trashtrack.ui.presentation.admin.waste_reported.ReportedWasteScreen
import com.samuelokello.trashtrack.ui.presentation.shared.auth.create_profile.CreateProfileScreen
import com.samuelokello.trashtrack.ui.presentation.shared.auth.sign_in.SignInScreen
import com.samuelokello.trashtrack.ui.presentation.shared.auth.sign_up.SignUpScreen
import com.samuelokello.trashtrack.ui.presentation.shared.welcome.WelcomeScreen
import com.samuelokello.trashtrack.ui.presentation.user.UserHomeScreen
import com.samuelokello.trashtrack.ui.presentation.user.report_waste.ReportWasteScreen
import com.samuelokello.trashtrack.ui.presentation.user.request_pick_up.RequestPickupScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Welcome.route,
) {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val userDao = db.userDao()
    val repository = RoomUserRepository(userDao = userDao)
    val viewModel = AppViewModel(repository)

    LaunchedEffect(viewModel) {
        viewModel.navigationEvent.observeForever{ screen ->
            navController.navigate(screen.name)
        }
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Welcome.route) { WelcomeScreen(navController) }
        composable(NavigationItem.Signup.route) { SignUpScreen(navController) }
        composable(NavigationItem.signin.route) { SignInScreen(navController) }
        composable(NavigationItem.CreateProfile.route) { CreateProfileScreen(navController) }
        composable(NavigationItem.Home.route) { UserHomeScreen(navController = navController) }
        composable(NavigationItem.Admin.route) { AdminHomeScreen(navController = navController) }
        composable(NavigationItem.Request.route) { RequestPickupScreen(navController) }
        composable(NavigationItem.Report.route) { ReportWasteScreen(navController) }
        composable(NavigationItem.PickUpRequest.route) { PickUpRequestScreen(navController) }
        composable(NavigationItem.ReportedWaste.route) { ReportedWasteScreen(navController = navController) }
    }
}