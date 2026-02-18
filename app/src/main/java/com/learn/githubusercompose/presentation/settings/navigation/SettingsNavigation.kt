package com.learn.githubusercompose.presentation.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.learn.githubusercompose.core.navigation.ScreenRoute
import com.learn.githubusercompose.presentation.settings.GithubTokenScreen
import com.learn.githubusercompose.presentation.settings.SettingsScreen


fun NavGraphBuilder.settingsGraph(
    navController: NavController
) {
    navigation<ScreenRoute.SettingsGraph>(
        startDestination = ScreenRoute.SettingsRoute
    ) {
        composable<ScreenRoute.SettingsRoute> {
            SettingsScreen(
                onGithubTokenClick = {
                    navController.navigateToGithubToken()
                }
            )
        }

        composable<ScreenRoute.GithubTokenRoute> {
            GithubTokenScreen()
        }
    }
}

fun NavController.navigateToSettings(navOptions: NavOptions? = null) =
    if (navOptions != null) {
        navigate(route = ScreenRoute.SettingsGraph, navOptions)
    } else {
        navigate(ScreenRoute.SettingsGraph)
    }

fun NavController.navigateToGithubToken() =
    navigate(ScreenRoute.GithubTokenRoute)

