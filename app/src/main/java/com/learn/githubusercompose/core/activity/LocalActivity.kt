package com.learn.githubusercompose.core.activity

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext

val LocalActivity: ProvidableCompositionLocal<ComponentActivity?> = compositionLocalOf { null }
val LocalAppSnackbarHostState = staticCompositionLocalOf<SnackbarHostState> {
    error("No SnackbarHostState provided")
}

@Composable
fun ProvideLocalActivity(
    activity: ComponentActivity? = LocalContext.current.findComponentActivity(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalActivity provides activity, content = content)
}

private tailrec fun Context.findComponentActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.findComponentActivity()
    else -> null
}