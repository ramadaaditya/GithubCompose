package com.learn.githubusercompose.core.navigation

import androidx.compose.foundation.layout.Box
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
import com.learn.githubusercompose.presentation.search.SearchScreen
import com.learn.githubusercompose.ui.components.BottomBar

@Composable
fun AppNavHost(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = ScreenRoute.HomeRoute,
            ) {
                composable<ScreenRoute.HomeRoute> {
                    HomeScreen(
                        navigateToDetail = { }
                    )
                }
                composable<ScreenRoute.ProfileRoute> {
                    ProfileScreen()
                }
                composable<ScreenRoute.SearchRoute> {
                    SearchScreen()
                }
                composable<ScreenRoute.NotificationRoute> {
                    NotificationScreen()
                }

                composable<ScreenRoute.DetailUserRoute> {
                    val id = it.arguments?.getLong("userId") ?: -1L
                    DetailScreen(
                        userId = id,
                        navigateBack = {
                            navController.navigateUp()
                        }
                    )
                }
            }
        }
    }
}