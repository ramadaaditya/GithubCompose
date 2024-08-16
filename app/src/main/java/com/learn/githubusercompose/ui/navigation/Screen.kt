package com.learn.githubusercompose.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Profile : Screen("profile")
    data object DetailUser : Screen("home/{userId}") {
        fun createRoute(userId: Long) = "home/$userId"
    }
}