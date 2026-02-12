package com.learn.githubusercompose.data

import com.learn.githubusercompose.core.common.ErrorType
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

sealed class Resource<T> {
    class Loading<T> : Resource<T>()
    data class Success<T>(
        val data: T
    ) : Resource<T>()

    data class Error<T>(
        val message: String?,
        val errorType: ErrorType = ErrorType.UNKNOWN

    ) : Resource<T>()
}

fun mapThrowableToErrorType(throwable: Throwable): ErrorType {
    return when (throwable) {

        is SocketTimeoutException -> ErrorType.TIMEOUT

        is IOException -> ErrorType.NETWORK

        is HttpException -> {
            when (throwable.code()) {
                400 -> ErrorType.BAD_REQUEST
                401 -> ErrorType.UNAUTHORIZED
                403 -> ErrorType.FORBIDDEN
                404 -> ErrorType.NOT_FOUND
                in 500..599 -> ErrorType.SERVER
                else -> ErrorType.UNKNOWN
            }
        }

        else -> ErrorType.UNKNOWN
    }
}