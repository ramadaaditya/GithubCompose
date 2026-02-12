package com.learn.githubusercompose.presentation.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.learn.githubusercompose.core.navigation.ScreenRoute

fun NavController.navigateToFavorite(navOptions: NavOptions? = null) =
    if (navOptions != null) {
        navigate(route = ScreenRoute.FavoriteRoute, navOptions)
    } else {
        navigate(ScreenRoute.FavoriteRoute)
    }


@Composable
fun FavoriteScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("This is Notification Screen")
    }

}