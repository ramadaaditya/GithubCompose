package com.learn.githubusercompose.core.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class ScreenRoute() {
    @Serializable
    object SplashRoute : ScreenRoute()

    @Serializable

    object HomeRoute : ScreenRoute()

    @Serializable

    object FavoriteRoute : ScreenRoute()

    @Serializable
    object ProfileRoute : ScreenRoute()

    @Serializable
    data class DetailUserRoute(val username: String) : ScreenRoute()

    @Serializable
    object NotificationRoute : ScreenRoute()

    @Serializable
    object SettingsRoute : ScreenRoute()

}

