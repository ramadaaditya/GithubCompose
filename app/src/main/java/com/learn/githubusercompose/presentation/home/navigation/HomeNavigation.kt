package com.learn.githubusercompose.presentation.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.learn.githubusercompose.core.navigation.ScreenRoute

fun NavController.navigateToHome(navOptions: NavOptions? = null) =
    if (navOptions != null) {
        navigate(route = ScreenRoute.HomeRoute, navOptions)
    } else {
        navigate(ScreenRoute.HomeRoute)
    }


fun NavController.navigateToDetail(navOptions: NavOptions? = null) =
    if (navOptions != null) {
        navigate(route = ScreenRoute.DetailUserRoute, navOptions)
    } else {
        navigate(ScreenRoute.DetailUserRoute)
    }

