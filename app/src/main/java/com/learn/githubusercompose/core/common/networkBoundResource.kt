package com.learn.githubusercompose.core.common

import com.learn.githubusercompose.data.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

/**
 * ResultType: Tipe data yang akan dikonsumsi UI (Domain Model)
 * RequestType: Tipe data yang didapat dari API (Response Model)
 */
inline fun <ResultType, RequestType> networkBoundResource(
    // 1. Logic untuk mengambil data dari Database (mengembalikan Flow)
    crossinline query: () -> Flow<ResultType>,

    // 2. Logic untuk mengambil data dari API (Suspend function)
    crossinline fetch: suspend () -> RequestType,

    // 3. Logic untuk menyimpan hasil API ke Database
    crossinline saveFetchResult: suspend (RequestType) -> Unit,

    // 4. Logic untuk menentukan apakah perlu fetch baru? (Default: Selalu fetch)
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
): Flow<Resource<ResultType>> = flow {

    // Tahap 1: Emit data awal dari DB (biasanya masih kosong atau data lama)
    val data = query().first()

    // Tahap 2: Cek apakah perlu ambil dari internet?
    val flow = if (shouldFetch(data)) {

        // Jika YA: Emit Loading (sambil membawa data lama jika ada agar UI tidak blank)
        emit(Resource.Loading(data))

        try {
            // Panggil API
            val apiResponse = fetch()

            // Simpan ke DB (Ini akan mentrigger Flow 'query' di bawah nanti)
            saveFetchResult(apiResponse)

            // Ambil stream data terbaru dari DB (yang barusan di-update)
            query().map { Resource.Success(it) }

        } catch (throwable: Throwable) {
            // Jika Gagal: Emit Error tapi TETAP bawa data lama (data dari DB)
            // Jadi user masih bisa lihat data cache meski internet mati
            query().map { Resource.Error(throwable.message ?: "Unknown Error", it) }
        }
    } else {
        // Jika TIDAK perlu fetch: Langsung emit data dari DB sebagai Success
        query().map { Resource.Success(it) }
    }

    // Tahap 3: Pancarkan Flow yang sudah ditentukan di atas
    emitAll(flow)
}