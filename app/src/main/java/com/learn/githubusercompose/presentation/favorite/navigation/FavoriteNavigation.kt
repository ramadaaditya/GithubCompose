package com.learn.githubusercompose.presentation.favorite.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.learn.githubusercompose.core.navigation.ScreenRoute
import com.learn.githubusercompose.presentation.favorite.FavoriteScreen
import com.learn.githubusercompose.presentation.home.navigation.navigateToDetail


fun NavGraphBuilder.favoriteGraph(
    navController: NavController
) {
    navigation<ScreenRoute.FavoriteGraph>(
        startDestination = ScreenRoute.FavoriteRoute
    ) {
        composable<ScreenRoute.FavoriteRoute> {
            FavoriteScreen(
                onClick = { username ->
                    navController.navigateToDetail(username)
                }
            )
        }
    }
}

fun NavController.navigateToFavorite(navOptions: NavOptions? = null) =
    if (navOptions != null) {
        navigate(route = ScreenRoute.FavoriteRoute, navOptions)
    } else {
        navigate(ScreenRoute.FavoriteRoute)
    }
