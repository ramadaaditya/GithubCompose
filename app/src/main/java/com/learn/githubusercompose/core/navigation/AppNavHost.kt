package com.learn.githubusercompose.core.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.learn.githubusercompose.presentation.SplashScreen
import com.learn.githubusercompose.presentation.detail.DetailScreen
import com.learn.githubusercompose.presentation.home.HomeScreen
import com.learn.githubusercompose.presentation.notification.NotificationScreen
import com.learn.githubusercompose.presentation.profile.ProfileScreen
import com.learn.githubusercompose.ui.components.BottomBar

@Composable
fun AppNavHost(navController: NavHostController) {
    val navBackStackEnty by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEnty?.destination

    val isSplashScreen = currentDestination?.hasRoute<ScreenRoute.SplashRoute>() == true
    val isDetailScreen = currentDestination?.hasRoute<ScreenRoute.DetailUserRoute>() == true
    val showBottomBar = !isSplashScreen && !isDetailScreen

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (showBottomBar) {
                BottomBar(navController)
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = ScreenRoute.SplashRoute
            ) {
                composable<ScreenRoute.HomeRoute>(
                    enterTransition = {
                        fadeIn(animationSpec = tween(durationMillis = 1000))
                    },
                    popEnterTransition = {
                        // Jika user kembali ke Home dari detail, gunakan fade juga
                        fadeIn(animationSpec = tween(500))
                    }
                ) {
                    HomeScreen(
                        navigateToDetail = { username ->
                            navController.navigate(ScreenRoute.DetailUserRoute(username))
                        }
                    )
                }
                composable<ScreenRoute.SplashRoute>(
                    exitTransition = {
                        fadeOut(animationSpec = tween(durationMillis = 500))
                    }
                ) {
                    SplashScreen(
                        onAnimationFinished = {
                            navController.navigate(ScreenRoute.HomeRoute) {
                                popUpTo<ScreenRoute.SplashRoute> { inclusive = true }
                            }
                        }
                    )
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