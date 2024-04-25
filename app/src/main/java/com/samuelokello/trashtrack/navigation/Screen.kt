package com.samuelokello.trashtrack.navigation

enum class Screen {
    WELCOME,
    SIGNUP,
    SIGN_IN,
    CREATE_PROFILE,
    HOME,
    REPORT,
    REQUEST,
}
sealed class NavigationItem(val route: String) {
    data object Welcome: NavigationItem(Screen.WELCOME.name)
    data object Signup : NavigationItem(Screen.SIGNUP.name)
    data object signin : NavigationItem(Screen.SIGN_IN.name)
    data object CreateProfile : NavigationItem(Screen.CREATE_PROFILE.name)
    data object Home : NavigationItem(Screen.HOME.name)
    data object Report : NavigationItem(Screen.REPORT.name)
    data object Request : NavigationItem(Screen.REQUEST.name)
}

fun getNavigationItem(route: String): NavigationItem {
    return when (route) {
        Screen.HOME.name -> NavigationItem.Home
        Screen.SIGN_IN.name -> NavigationItem.signin
        Screen.SIGNUP.name -> NavigationItem.Signup
        Screen.CREATE_PROFILE.name -> NavigationItem.CreateProfile
        Screen.REPORT.name -> NavigationItem.Report
        Screen.REQUEST.name -> NavigationItem.Request
        else -> NavigationItem.Home
    }
}