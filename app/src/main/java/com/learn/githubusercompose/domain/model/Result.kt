package com.learn.githubusercompose.domain.model

import com.learn.githubusercompose.core.common.ErrorType

sealed class Result<out T> {
    data object Loading : Result<Nothing>()
    data class Success<out T>(
        val data: T
    ) : Result<T>()

    data class Error<T>(
        val message: String?,
        val errorType: ErrorType = ErrorType.UNKNOWN

    ) : Result<T>()
}

