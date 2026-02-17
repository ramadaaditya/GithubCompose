package com.learn.githubusercompose.presentation.mapper


import com.learn.githubusercompose.core.common.UiState
import com.learn.githubusercompose.domain.model.Result

fun <T, R> Result<T>.toUiState(transform: (T) -> R): UiState<R> {
    return when (this) {
        is Result.Loading -> UiState.Loading
        is Result.Success -> UiState.Success(transform(data))
        is Result.Error -> UiState.Error(
            errorMessage = message ?: "Unknown error",
            errorType = errorType
        )
    }
}

// Untuk kasus tanpa transformasi
fun <T> Result<T>.toUiState(): UiState<T> {
    return when (this) {
        is Result.Loading -> UiState.Loading
        is Result.Success -> UiState.Success(data)
        is Result.Error -> UiState.Error(
            errorMessage = message ?: "Unknown error",
            errorType = errorType
        )
    }
}