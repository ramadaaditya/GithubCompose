package com.learn.githubusercompose.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext


private val DarkColorScheme = darkColorScheme(
    // Background colors
    background = Color(0xFF1A1F2E),        // Main dark blue background
    surface = Color(0xFF252D3F),           // Card/elevated surface color
    surfaceVariant = Color(0xFF2A3346),    // Search bar, chips background

    // Primary colors (for buttons, chips)
    primary = Color(0xFF4D6EFF),           // Blue accent (Follow button, selected chip)
    onPrimary = Color.White,               // Text on primary button
    primaryContainer = Color(0xFF3D5ACC),  // Darker blue for pressed state

    // Text colors
    onBackground = Color.White,            // Main text color
    onSurface = Color.White,               // Text on cards
    onSurfaceVariant = Color(0xFF9CA3B4),  // Secondary text (username, location)

    // Outline/border colors
    outline = Color(0xFF3A4253),           // Card borders, dividers
    outlineVariant = Color(0xFF2D3444),    // Subtle borders

    // Additional colors
    secondary = Color(0xFF6C7A94),         // Icon colors, inactive states
    tertiary = Color(0xFF00D9FF),          // Verification badge
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun GithubUserComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}