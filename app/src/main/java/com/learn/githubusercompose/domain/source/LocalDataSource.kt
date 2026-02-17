package com.learn.githubusercompose.domain.source

import com.learn.githubusercompose.data.local.entity.DetailUserEntity
import com.learn.githubusercompose.data.local.entity.FavoriteUserEntity
import com.learn.githubusercompose.data.local.entity.FollowerEntity
import com.learn.githubusercompose.data.local.entity.FollowingEntity
import com.learn.githubusercompose.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    // Search / Users
//    fun searchUsers(query: String): Flow<List<UserEntity>>
    suspend fun insertUsers(users: List<UserEntity>)

    fun getDetailUser(username: String): Flow<DetailUserEntity?>
    suspend fun insertDetailUser(user: DetailUserEntity)

    fun getFollowing(username: String): Flow<List<FollowingEntity>>
    suspend fun updateFollowing(username: String, list: List<FollowingEntity>)

    fun getFollowers(username: String): Flow<List<FollowerEntity>>
    suspend fun updateFollowers(username: String, list: List<FollowerEntity>)

    fun getAllFavorites(): Flow<List<FavoriteUserEntity>>
    suspend fun insertFavorite(user: FavoriteUserEntity)
    suspend fun deleteFavoriteById(id: Int)
//    suspend fun isFavorite(username: String): Boolean

    fun getAllUsers(): Flow<List<UserEntity>>

    suspend fun replaceUsers(users: List<UserEntity>)
    suspend fun getUserById(id: Int): UserEntity?
}