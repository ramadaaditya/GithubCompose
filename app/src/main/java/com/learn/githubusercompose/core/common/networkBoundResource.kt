package com.learn.githubusercompose.core.common

import com.learn.githubusercompose.data.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * ResultType: Tipe data yang akan dikonsumsi UI (Domain Model)
 * RequestType: Tipe data yang didapat dari API (Response Model)
 */
inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
): Flow<Resource<ResultType>> = flow {
    emit(Resource.Loading(null))
    var hasFetched = false

    query().collect { data ->
        val needFetch = shouldFetch(data) && !hasFetched

        if (needFetch) {
            emit(Resource.Loading(data))
            try {
                val response = fetch()
                saveFetchResult(response)
                hasFetched = true
            } catch (throwable: Throwable) {
                emit(
                    Resource.Error(
                        throwable.message ?: "Unknown Error",
                        data
                    )
                )
                hasFetched = true
            }
        } else {
            emit(Resource.Success(data))
        }
    }
}
