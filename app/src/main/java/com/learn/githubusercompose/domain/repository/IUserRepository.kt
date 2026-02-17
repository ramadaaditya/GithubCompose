package com.learn.githubusercompose.domain.repository

import com.learn.githubusercompose.domain.model.DetailUser
import com.learn.githubusercompose.domain.model.Result
import com.learn.githubusercompose.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun searchUsers(query: String): Flow<Result<List<User>>>
    fun getDetailUser(username: String): Flow<Result<DetailUser?>>
    fun getFollowing(username: String): Flow<Result<List<User>>>
    fun getFollowers(username: String): Flow<Result<List<User>>>

    fun getAllFavorites(): Flow<Result<List<User>>>
    fun getUsersStream(): Flow<List<User>>
    suspend fun insertFavorite(user: User)
    suspend fun deleteFavorite(id: Int)
}