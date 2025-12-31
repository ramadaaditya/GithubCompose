package com.learn.githubusercompose.data

import android.content.ContentValues.TAG
import android.util.Log
import com.learn.githubusercompose.data.remote.api.ApiServices
import com.learn.githubusercompose.data.remote.response.ItemsItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val apiService: ApiServices
) {
    suspend fun searchUsers(query: String): List<ItemsItem> {
        return try {
            val response = apiService.searchUser(query)
            response.items
        } catch (e: Exception) {
            Log.e(TAG, "searchUsers: Terjadi kesalahan ${e.localizedMessage}")
            emptyList()
        }
    }
}