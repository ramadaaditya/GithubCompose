package com.learn.githubusercompose.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.learn.githubusercompose.core.common.networkBoundResource
import com.learn.githubusercompose.data.Resource
import com.learn.githubusercompose.data.local.dao.DetailUserDao
import com.learn.githubusercompose.data.local.dao.FollowDao
import com.learn.githubusercompose.data.local.dao.SearchUserDao
import com.learn.githubusercompose.data.remote.api.ApiServices
import com.learn.githubusercompose.data.remote.dto.toDomain
import com.learn.githubusercompose.data.remote.dto.toEntity
import com.learn.githubusercompose.data.remote.dto.toFollowerEntity
import com.learn.githubusercompose.data.remote.dto.toFollowingEntity
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
    private val userDao: SearchUserDao,
    private val detailDao: DetailUserDao,
    private val followDao: FollowDao
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

    override fun getDetailUser(username: String): Flow<Resource<DetailUser?>> {
        return networkBoundResource(
            query = {
                detailDao.getDetailUser(username).map { entity ->
                    entity?.toDomain()
                }
            },
            fetch = {
                apiService.getDetailUser(username)
            },
            saveFetchResult = { response ->
                val entity = response.toEntity()
                detailDao.insertDetailUser(entity)
            },
            shouldFetch = { data ->
                data == null
            }
        )
    }

    override fun getFollowing(username: String): Flow<Resource<List<UserItemUiState>>> {
        return networkBoundResource(
            query = {
                followDao.getFollowing(username).map { entities ->
                    entities.map { it.toDomain() }
                }
            },
            fetch = {
                apiService.getFollowing(username)
            },
            saveFetchResult = { response ->
                val entity = response.map {
                    it.toFollowingEntity(owner = username)
                }
                followDao.deleteFollowersByUsername(username)
                followDao.insertFollowing(entity)
            },
            shouldFetch = { data ->
                data.isEmpty()
            }
        )
    }

    override fun getFollowers(username: String): Flow<Resource<List<UserItemUiState>>> {
        return networkBoundResource(
            query = {
                followDao.getFollowers(username).map { entities ->
                    entities.map { it.toDomain() }
                }
            },
            fetch = {
                apiService.getFollowers(username)
            },
            saveFetchResult = { response ->
                val entity = response.map {
                    it.toFollowerEntity(owner = username)
                }
                followDao.insertFollowers(entity)
            },
            shouldFetch = { data ->
                data.isEmpty()
            }
        )
    }


    fun getUsersStream(): Flow<List<UserItemUiState>> {
        return userDao.getAllUser().map { entities ->
            entities.toUserDomains()
        }
    }
}