package com.learn.githubusercompose.core.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val screenRoute: ScreenRoute
)
