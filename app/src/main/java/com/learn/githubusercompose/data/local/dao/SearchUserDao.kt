package com.learn.githubusercompose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.learn.githubusercompose.data.local.entity.SearchUserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchUserDao {
    @Query("SELECT * FROM users")
    fun getAllUser(): Flow<List<SearchUserEntity>>

    @Query("SELECT * FROM users WHERE is_favorite = 1")
    fun getFavoriteUsers(): Flow<List<SearchUserEntity>>

    @Query("SELECT EXISTS(SELECT * FROM users WHERE username = :username)")
    suspend fun isUserExist(username: String): Boolean

    @Query("SELECT id FROM users WHERE is_favorite = 1")
    suspend fun getFavoriteUserIds():List<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<SearchUserEntity>)

    @Query("UPDATE users SET is_favorite = :isFavorite WHERE username = :username")
    suspend fun updateFavoriteStatus(username: String, isFavorite: Boolean)

    @Query("DELETE FROM users WHERE is_favorite = 0")
    suspend fun deleteAllNonFavorites()
}