package com.learn.githubusercompose.core.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class ScreenRoute() {
    @Serializable
    object SplashRoute : ScreenRoute()


    @Serializable
    object HomeGraph : ScreenRoute()

    @Serializable
    object HomeRoute : ScreenRoute()

    @Serializable
    data class DetailRoute(val username: String) : ScreenRoute()

    @Serializable
    object FavoriteGraph : ScreenRoute()

    @Serializable
    object FavoriteRoute : ScreenRoute()


    @Serializable
    object SettingsGraph : ScreenRoute()

    @Serializable
    object GithubTokenRoute : ScreenRoute()

    @Serializable
    object SettingsRoute : ScreenRoute()

}

