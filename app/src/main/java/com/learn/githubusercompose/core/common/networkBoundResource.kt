package com.learn.githubusercompose.core.common

import android.util.Log
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
    val dbSource = query()
    val dbData = query().first()

    if (shouldFetch(dbData)) {
        emit(Result.Loading)
        try {
            val response = fetch()
            saveFetchResult(response)
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
                return@flow
            } else {
                // Skenario B: Data lokal ADA + Network GAGAL = Silent Fail
                // Jangan emit Result.Error karena akan menimpa layar data dengan layar error.
                // Cukup log error-nya, dan biarkan kode lanjut ke bawah untuk emit data lokal.
                Log.e(
                    "NetworkBoundResource",
                    "Fetch failed but displaying cached data: ${throwable.message}"
                )

                // Opsional: Anda bisa emit Result.Error tipe khusus (misal: "ToastError")
                // jika ingin UI menampilkan Snackbar "No Internet" tapi list tetap muncul.

            }
        }
    }

    emitAll(
        query().map { Result.Success(it) }
    )
}

