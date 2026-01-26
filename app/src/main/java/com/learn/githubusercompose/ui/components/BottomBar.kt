@file:OptIn(ExperimentalMaterial3Api::class)

package com.learn.githubusercompose.ui.components

import android.content.res.Configuration
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.learn.githubusercompose.core.navigation.NavigationItem
import com.learn.githubusercompose.core.navigation.navigationItem
import com.learn.githubusercompose.ui.theme.GithubUserComposeTheme

@Composable
fun BottomBar(
    navController: NavHostController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomBarContent(
        currentRoute = currentRoute,
        onNavigate = { destination ->
            navController.navigate(destination.screenRoute) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                restoreState = true
                launchSingleTop = true
            }
        }
    )
}

@Composable
fun BottomBarContent(
    currentRoute: String?,
    onNavigate: (NavigationItem) -> Unit
) {
    BottomAppBar {
        navigationItem.forEach { destination ->
            val isSelected = currentRoute == destination.screenRoute::class.qualifiedName

            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(destination) },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = destination.title
                    )
                },
                label = {
                    Text(
                        text = destination.title,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,

                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = Color.Transparent,
                )
            )
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BottomBarPreview() {
    GithubUserComposeTheme {
        val sampleSelectedRoute = navigationItem.firstOrNull()?.screenRoute!!::class.qualifiedName
        BottomBarContent(
            currentRoute = sampleSelectedRoute,
            onNavigate = {}
        )
    }
}