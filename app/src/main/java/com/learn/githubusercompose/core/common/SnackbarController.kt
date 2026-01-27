package com.learn.githubusercompose.core.common

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

data class SnackbarEvent(
    val message: String,
    val type: SnackbarKind = SnackbarKind.User,
    val actionLabel: String? = null,
    val onAction: (() -> Unit)? = null
)

data class SnackbarAction(
    val name: String,
    val action: suspend () -> Unit
)

enum class SnackbarKind {
    User,
    System,
}

object SnackbarManager {
    private val _events = Channel<SnackbarEvent>()
    val events = _events.receiveAsFlow()

    suspend fun show(
        message: String,
        type: SnackbarKind = SnackbarKind.User,
        actioLabel: String? = null,
        onAction: (() -> Unit)? = null
    ) {
        _events.send(SnackbarEvent(message, type, actioLabel, onAction))
    }
}