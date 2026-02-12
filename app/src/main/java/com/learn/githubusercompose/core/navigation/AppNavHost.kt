package com.learn.githubusercompose.core.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.learn.githubusercompose.presentation.SplashScreen
import com.learn.githubusercompose.presentation.detail.DetailScreen
import com.learn.githubusercompose.presentation.favorite.FavoriteScreen
import com.learn.githubusercompose.presentation.home.HomeScreen
import com.learn.githubusercompose.presentation.settings.navigation.settingsGraph
import com.learn.githubusercompose.ui.GithubAppState

@Composable
fun AppNavHost(
    appState: GithubAppState,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.SplashRoute
    ) {
        composable<ScreenRoute.HomeRoute>(
            enterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 1000))
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(500))
            }
        ) {
            HomeScreen(
                navigateToDetail = { username ->
                    navController.navigate(ScreenRoute.DetailUserRoute(username))
                }
            )
        }

        settingsGraph(
            navController = navController
        )
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
        composable<ScreenRoute.FavoriteRoute> {
            FavoriteScreen()
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
