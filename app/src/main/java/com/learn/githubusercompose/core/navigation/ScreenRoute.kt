package com.learn.githubusercompose.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import kotlinx.serialization.Serializable

@Serializable
sealed class ScreenRoute() {
    @Serializable
    object SplashRoute : ScreenRoute()

    @Serializable

    object HomeRoute : ScreenRoute()

    @Serializable
    object ProfileRoute : ScreenRoute()

    @Serializable
    data class DetailUserRoute(val username: String) : ScreenRoute()

    @Serializable
    object NotificationRoute : ScreenRoute()
}


val navigationItem = listOf(
    NavigationItem(
        title = "Home",
        icon = Icons.Default.Home,
        screenRoute = ScreenRoute.HomeRoute
    ),
    NavigationItem(
        title = "Notification",
        icon = Icons.Default.Notifications,
        screenRoute = ScreenRoute.NotificationRoute
    ),
    NavigationItem(
        title = "Profile",
        icon = Icons.Default.Person,
        screenRoute = ScreenRoute.ProfileRoute
    )
)