package com.learn.githubusercompose.core.common

import com.learn.githubusercompose.data.Resource
import com.learn.githubusercompose.data.mapThrowableToErrorType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
): Flow<Resource<ResultType>> = flow {
    emit(Resource.Loading())
    val data = query().first()
    if (shouldFetch(data)) {
        emit(Resource.Loading())
        try {
            val response = fetch()
            saveFetchResult(response)
        } catch (throwable: Throwable) {
            val errorType = mapThrowableToErrorType(throwable)

            emit(
                Resource.Error(
                    throwable.message ?: "Unknown Error",
                    errorType = errorType
                )
            )
            return@flow
        }
    }

    emitAll(
        query().map { Resource.Success(it) }
    )
}

