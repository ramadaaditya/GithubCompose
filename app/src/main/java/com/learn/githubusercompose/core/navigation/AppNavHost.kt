package com.learn.githubusercompose.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.learn.githubusercompose.ui.components.BottomBar

@Composable
fun AppNavHost(navController: NavHostController) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = ScreenRoute.HomeRoute,
            ) {
                composable<ScreenRoute.HomeRoute> {
                    HomeScreen()
                }
                composable<ScreenRoute.ProfileRoute> {
                    ProfileScreen()
                }
                composable<ScreenRoute.NotificationRoute> {
                    NotificationScreen()
                }

                composable<ScreenRoute.DetailUserRoute> {
                    DetailScreen(
                        navigateBack = {
                            navController.navigateUp()
                        }
                    )
                }
            }
        }
    }
}