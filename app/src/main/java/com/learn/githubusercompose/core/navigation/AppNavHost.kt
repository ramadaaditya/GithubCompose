package com.learn.githubusercompose.core.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.learn.githubusercompose.presentation.SplashScreen
import com.learn.githubusercompose.presentation.detail.DetailScreen
import com.learn.githubusercompose.presentation.favorite.navigation.favoriteGraph
import com.learn.githubusercompose.presentation.home.navigation.homeGraph
import com.learn.githubusercompose.presentation.settings.navigation.settingsGraph
import com.learn.githubusercompose.ui.GithubAppState

@Composable
fun AppNavHost(
    appState: GithubAppState,
    innerPadding: PaddingValues
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.SplashRoute
    ) {

        homeGraph(navController = navController, innerPadding = innerPadding)
        settingsGraph(
            navController = navController
        )
        favoriteGraph(
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


        composable<ScreenRoute.DetailRoute> {
            DetailScreen(
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}
