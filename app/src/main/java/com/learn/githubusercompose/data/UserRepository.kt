package com.learn.githubusercompose.data

import android.content.ContentValues.TAG
import android.util.Log
import com.learn.githubusercompose.data.local.dao.SearchUserDao
import com.learn.githubusercompose.data.local.entity.UserEntity
import com.learn.githubusercompose.data.remote.api.ApiServices
import com.learn.githubusercompose.data.remote.dto.toUserEntities
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val apiService: ApiServices,
    private val userDao: SearchUserDao
) {
    fun searchUsers(query: String): Flow<Result<List<UserEntity>>> = flow {
        try {
            val response = apiService.searchUser(query)
            val data = response.items.toUserEntities()
            userDao.deleteAllNonFavorites()
            userDao.insertUsers(data)

            emit(Result.success(data))
        } catch (e: Exception) {
            Log.e(TAG, "searchUsers: Terjadi kesalahan ${e.localizedMessage}")
            emit(Result.failure(e))
        }
    }

    fun getUsersStream() : Flow<List<UserEntity>> = userDao.getAllUser()
}