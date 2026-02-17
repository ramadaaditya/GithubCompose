package com.learn.githubusercompose.core.common

import com.learn.githubusercompose.domain.model.ErrorMapper.mapThrowableToErrorType
import com.learn.githubusercompose.domain.model.Result
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
): Flow<Result<ResultType>> = flow {
    emit(Result.Loading)
    val dbData = query().first()

    if (shouldFetch(dbData)) {
        emit(Result.Loading)
        try {
            val response = fetch()
            saveFetchResult(response)
            emitAll(query().map { Result.Success(it) })
        } catch (throwable: Throwable) {
            val isLocalDataEmpty = (dbData as? List<*>)?.isEmpty() == true || dbData == null

            if (isLocalDataEmpty) {

                val errorType = mapThrowableToErrorType(throwable)


                emit(
                    Result.Error(
                        throwable.message ?: "Unknown Error",
                        errorType = errorType
                    )
                )
            } else {
                emitAll(query().map { Result.Success(it) })
            }
        }
    } else {
        emitAll(query().map { Result.Success(it) })
    }
}