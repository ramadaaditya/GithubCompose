package com.learn.githubusercompose.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.learn.githubusercompose.R
import com.learn.githubusercompose.core.navigation.ScreenRoute

fun NavController.navigateToSettings(navOptions: NavOptions? = null) =
    if (navOptions != null) {
        navigate(route = ScreenRoute.SettingsRoute, navOptions)
    } else {
        navigate(ScreenRoute.SettingsRoute)
    }


@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.profile),
            contentDescription = "Profile",
            contentScale = ContentScale.Fit,
            alignment = Alignment.Center,
            modifier = Modifier
                .padding(8.dp)
                .size(200.dp)
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = stringResource(R.string.name_dicoding),
            fontSize = 20.sp,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(8.dp)
        )
        Text(text = stringResource(R.string.email_dicoding))
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}