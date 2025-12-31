package com.learn.githubusercompose.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.learn.githubusercompose.presentation.detail.DetailScreen
import com.learn.githubusercompose.presentation.home.HomeScreen
import com.learn.githubusercompose.presentation.notification.NotificationScreen
import com.learn.githubusercompose.presentation.profile.ProfileScreen
import com.learn.githubusercompose.presentation.search.SearchScreen
import com.learn.githubusercompose.ui.components.BottomBar
import kotlinx.serialization.Serializable

@Serializable
sealed class ScreenRoute() {
    @Serializable
    object HomeRoute : ScreenRoute()

    @Serializable
    object ProfileRoute : ScreenRoute()

    @Serializable
    object DetailUserRoute : ScreenRoute()

    @Serializable
    object SearchRoute : ScreenRoute()

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
        title = "Search",
        icon = Icons.Default.Search,
        screenRoute = ScreenRoute.SearchRoute
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