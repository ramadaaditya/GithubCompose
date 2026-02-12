package com.learn.githubusercompose.presentation.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.learn.githubusercompose.core.navigation.ScreenRoute
import com.learn.githubusercompose.presentation.settings.GithubTokenScreen
import com.learn.githubusercompose.presentation.settings.SettingsScreen
import kotlinx.serialization.Serializable


@Serializable
object SettingsGraph

@Serializable
object GithubTokenRoute

fun NavGraphBuilder.settingsGraph(
    navController: NavController
) {
    navigation<SettingsGraph>(
        startDestination = ScreenRoute.SettingsRoute
    ) {
        composable<ScreenRoute.SettingsRoute> {
            SettingsScreen(
                onGithubTokenClick = {
                    navController.navigateToGithubToken()
                }
            )
        }

        composable<GithubTokenRoute> {
            GithubTokenScreen()
        }
    }
}

fun NavController.navigateToSettings(navOptions: NavOptions? = null) =
    if (navOptions != null) {
        navigate(route = SettingsGraph, navOptions)
    } else {
        navigate(SettingsGraph)
    }

fun NavController.navigateToGithubToken() =
    navigate(GithubTokenRoute)

