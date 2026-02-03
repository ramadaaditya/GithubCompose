package com.learn.githubusercompose.core.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.learn.githubusercompose.R
import com.learn.githubusercompose.core.navigation.ScreenRoute.FavoriteRoute
import com.learn.githubusercompose.core.navigation.ScreenRoute.HomeRoute
import com.learn.githubusercompose.core.navigation.ScreenRoute.SettingsRoute
import com.learn.githubusercompose.ui.GithubIcons
import kotlin.reflect.KClass

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val iconTextId: Int,
    @StringRes val titleTextId: Int,
    val route: KClass<*>,
    val baseRoute: KClass<*> = route
) {
    HOME(
        selectedIcon = GithubIcons.selectedHome,
        unselectedIcon = GithubIcons.unselectedHome,
        iconTextId = R.string.home,
        titleTextId = R.string.home,
        route = HomeRoute::class,
    ),
    FAVORITE(
        selectedIcon = GithubIcons.selectedFavorite,
        unselectedIcon = GithubIcons.unselectedFavorite,
        iconTextId = R.string.favorite,
        titleTextId = R.string.favorite,
        route = FavoriteRoute::class,
        ),
    SETTINGS(
        selectedIcon = GithubIcons.selectedSetting,
        unselectedIcon = GithubIcons.unselectedSetting,
        iconTextId = R.string.settings,
        titleTextId = R.string.settings,
        route = SettingsRoute::class,
    ),
}