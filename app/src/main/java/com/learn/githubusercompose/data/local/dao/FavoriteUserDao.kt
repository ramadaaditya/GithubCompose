package com.learn.githubusercompose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.learn.githubusercompose.data.local.entity.FavoriteUserEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface FavoriteUserDao {
    @Query("SELECT * FROM favorite_users ORDER BY added_at DESC")
    fun getAllFavorites(): Flow<List<FavoriteUserEntity>>

    @Query("SELECT * FROM favorite_users WHERE username = :username LIMIT 1")
    suspend fun getFavoriteByUsername(username: String): FavoriteUserEntity?

    @Query("SELECT username FROM favorite_users")
    suspend fun getFavoriteUsernames(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(user: FavoriteUserEntity)

    @Query("DELETE FROM favorite_users WHERE username = :username")
    suspend fun deleteFavoriteByUsername(username: String)

    @Query("SELECT id FROM favorite_users")
    suspend fun getAllFavoriteIds(): List<Int>


}