package com.learn.githubusercompose

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.learn.githubusercompose.ui.components.BottomBar
import com.learn.githubusercompose.ui.navigation.Screen
import com.learn.githubusercompose.ui.screen.detail.DetailScreen
import com.learn.githubusercompose.ui.screen.home.HomeScreen
import com.learn.githubusercompose.ui.screen.profile.ProfileScreen
import com.learn.githubusercompose.ui.theme.GithubUserComposeTheme

@Composable
fun GithubUserComposeApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentState = navBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            if (currentState != Screen.DetailUser.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { userId ->
                        navController.navigate(Screen.DetailUser.createRoute(userId))
                    }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(
                route = Screen.DetailUser.route,
                arguments = listOf(navArgument("userId") { type = NavType.LongType }),
            ) {
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

@Preview(showBackground = true)
@Composable
fun GithubUserComposeAppPreview() {
    GithubUserComposeTheme {
        GithubUserComposeApp()
    }
}