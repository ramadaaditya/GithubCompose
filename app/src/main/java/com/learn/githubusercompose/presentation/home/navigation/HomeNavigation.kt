package com.learn.githubusercompose.presentation.home.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.learn.githubusercompose.core.navigation.ScreenRoute
import com.learn.githubusercompose.presentation.home.HomeScreen


fun NavController.navigateToHome(navOptions: NavOptions? = null) =
    if (navOptions != null) {
        navigate(route = ScreenRoute.HomeRoute, navOptions)
    } else {
        navigate(ScreenRoute.HomeRoute)
    }


fun NavController.navigateToDetail(
    username: String,
) {
    navigate(route = ScreenRoute.DetailRoute(username))
}


fun NavGraphBuilder.homeGraph(
    navController: NavController,
    innerPadding: PaddingValues
) {
    navigation<ScreenRoute.HomeGraph>(
        startDestination = ScreenRoute.HomeRoute
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
                    navController.navigate(ScreenRoute.DetailRoute(username))
                },
                innerPadding = innerPadding
            )
        }
    }
}

