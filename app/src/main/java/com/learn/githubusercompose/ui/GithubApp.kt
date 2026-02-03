package com.learn.githubusercompose.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.learn.githubusercompose.core.activity.LocalAppSnackbarHostState
import com.learn.githubusercompose.core.navigation.AppNavHost

@Composable
fun GithubApp(
    appState: GithubAppState,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }

    CompositionLocalProvider(LocalAppSnackbarHostState provides snackbarHostState) {
        val topLevelDestination = appState.topLevelDestinations
        val currentDestination = appState.currentDestination
        val showBottomNav =
            currentDestination?.hierarchy?.any { dest ->
                topLevelDestination.any { item -> dest.hasRoute(item.route) }
            } == true

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            bottomBar = {
                AnimatedVisibility(
                    visible = showBottomNav,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Column {
                        BottomAppBar {
                            topLevelDestination.forEach { destination ->
                                val selected =
                                    currentDestination.isRouteInHierarchy(destination.baseRoute)

                                NavigationBarItem(
                                    selected = selected,
                                    onClick = {
                                        appState.navigateToTopLevelDestination(destination)
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = if (selected) destination.selectedIcon else destination.unselectedIcon,
                                            contentDescription = null
                                        )
                                    },
                                    label = { Text(stringResource(destination.iconTextId)) },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = MaterialTheme.colorScheme.primary,
                                        selectedTextColor = MaterialTheme.colorScheme.primary,
                                        unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                                        unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                                        indicatorColor = Transparent
                                    )
                                )
                            }
                        }
                    }
                }

            }
        ) { innerPadding ->
            AppNavHost(
                appState = appState,
//                modifier = modifier.padding(innerPadding)
            )
        }
    }
}