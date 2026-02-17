package com.learn.githubusercompose.domain.model

import com.learn.githubusercompose.core.common.ErrorType
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

object ErrorMapper {
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

    fun getErrorMessage(throwable: Throwable): String {
        return when (throwable) {
            is SocketTimeoutException -> "Connection timeout"
            is IOException -> "Connection timeout"
            is HttpException -> "Connection timeout"
            else -> throwable.message ?: "Unknown error occured"
        }
    }
}