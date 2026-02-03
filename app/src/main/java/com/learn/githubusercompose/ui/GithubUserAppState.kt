package com.learn.githubusercompose.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.util.trace
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.learn.githubusercompose.core.navigation.ScreenRoute
import com.learn.githubusercompose.core.navigation.TopLevelDestination
import com.learn.githubusercompose.presentation.home.navigation.navigateToHome
import com.learn.githubusercompose.presentation.notification.navigateToFavorite
import com.learn.githubusercompose.presentation.profile.navigateToSettings
import kotlin.reflect.KClass

@Composable
fun rememberGithubAppState(
    navController: NavHostController = rememberNavController()
): GithubAppState {
    return remember(navController) {
        GithubAppState(
            navController,
        )
    }
}

@Stable
class GithubAppState(
    val navController: NavHostController,
) {
    private val previousDestination = mutableStateOf<NavDestination?>(null)
    val currentDestination: NavDestination?
        @Composable get() {
            val currentEntry = navController.currentBackStackEntryFlow
                .collectAsState(initial = null)

            return currentEntry.value?.destination.also { destination ->
                if (destination != null) {
                    previousDestination.value = destination
                }
            } ?: previousDestination.value
        }

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() {
            return TopLevelDestination.entries.firstOrNull { topLevelDestination ->
                currentDestination.isRouteInHierarchy(topLevelDestination.baseRoute)
            }
        }
    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries


    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation : ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {

                popUpTo<ScreenRoute.HomeRoute> {
                    saveState = true
                }

                launchSingleTop = true
                restoreState = true
            }

            when (topLevelDestination) {
                TopLevelDestination.HOME -> navController.navigateToHome(topLevelNavOptions)
                TopLevelDestination.FAVORITE -> navController.navigateToFavorite(topLevelNavOptions)
                TopLevelDestination.SETTINGS -> navController.navigateToSettings(topLevelNavOptions)
            }
        }
    }
}

fun NavDestination?.isRouteInHierarchy(route: KClass<*>): Boolean {
    return this?.hierarchy?.any { it.hasRoute(route) } == true
}