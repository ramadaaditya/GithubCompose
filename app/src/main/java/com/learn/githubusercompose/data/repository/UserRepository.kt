package com.learn.githubusercompose.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.learn.githubusercompose.data.Resource
import com.learn.githubusercompose.data.local.dao.SearchUserDao
import com.learn.githubusercompose.data.remote.api.ApiServices
import com.learn.githubusercompose.data.remote.dto.toDetailUserDomain
import com.learn.githubusercompose.data.remote.dto.toFollowDomain
import com.learn.githubusercompose.data.remote.dto.toUserDomains
import com.learn.githubusercompose.data.remote.dto.toUserEntities
import com.learn.githubusercompose.domain.model.DetailUser
import com.learn.githubusercompose.domain.model.UserItemUiState
import com.learn.githubusercompose.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val apiService: ApiServices,
    private val userDao: SearchUserDao
) : IUserRepository {
    override fun searchUsers(query: String): Flow<Resource<List<UserItemUiState>>> = flow {
        try {
            val response = apiService.searchUser(query)
            val data = response.items.toUserEntities()
            val usersFavorite = userDao.getFavoriteUserIds()
            val insertFavorite = data.map { user ->
                if (usersFavorite.contains(user.id)) {
                    user.copy(isFavorite = true)
                } else {
                    user
                }
            }
            userDao.deleteAllNonFavorites()
            userDao.insertUsers(insertFavorite)
            val domain = data.toUserDomains()
            emit(Resource.Success(domain))
        } catch (e: Exception) {
            Log.e(TAG, "searchUsers: Terjadi kesalahan ${e.localizedMessage}")
            emit(Resource.Error(e.localizedMessage ?: "Terjadi kesalahan yang tidak diketahui"))
        }
    }

    override fun getDetailUser(username: String): Flow<Resource<DetailUser>> = flow {
        try {
            val response = apiService.getDetailUser(username)
            val data = response.toDetailUserDomain()
            emit(Resource.Success(data))
        } catch (e: Exception) {
            Log.e(TAG, "searchUsers: Terjadi kesalahan ${e.localizedMessage}")
            emit(Resource.Error(e.localizedMessage ?: "Terjadi kesalahan yang tidak diketahui"))
        }
    }

    override fun getFollowing(username: String): Flow<Resource<List<UserItemUiState>>> = flow {
        try {
            val response = apiService.getFollowing(username)
            val data = response.toFollowDomain()
            emit(Resource.Success(data))
        } catch (e: Exception) {
            Log.e(TAG, "searchUsers: Terjadi kesalahan ${e.localizedMessage}")
            emit(Resource.Error(e.localizedMessage ?: "Terjadi kesalahan yang tidak diketahui"))
        }
    }

    override fun getFollowers(username: String): Flow<Resource<List<UserItemUiState>>> = flow {
        try {
            val response = apiService.getFollowers(username)
            val data = response.toFollowDomain()
            emit(Resource.Success(data))
        } catch (e: Exception) {
            Log.e(TAG, "searchUsers: Terjadi kesalahan ${e.localizedMessage}")
            emit(Resource.Error(e.localizedMessage ?: "Terjadi kesalahan yang tidak diketahui"))
        }
    }

    fun getUsersStream(): Flow<List<UserItemUiState>> {
        return userDao.getAllUser().map { entities ->
            entities.toUserDomains()
        }
    }
}