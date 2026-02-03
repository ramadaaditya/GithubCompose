package com.learn.githubusercompose.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val screenRoute: ScreenRoute
)


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