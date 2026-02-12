package com.learn.githubusercompose.core.common

sealed interface UiState<out T> {

    data object Loading : UiState<Nothing>

    data class Success<T>(val data: T) : UiState<T>
    data class Error(
        val errorMessage: String,
        val errorType: ErrorType = ErrorType.UNKNOWN
    ) : UiState<Nothing>
}


enum class ErrorType {
    NETWORK,
    TIMEOUT,
    UNAUTHORIZED,  // 401
    BAD_REQUEST,  // 401
    FORBIDDEN,     // 403
    NOT_FOUND,     // 404
    SERVER,
    UNKNOWN
}
