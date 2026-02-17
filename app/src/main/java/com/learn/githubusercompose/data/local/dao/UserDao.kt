package com.learn.githubusercompose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.learn.githubusercompose.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY username ASC")
    fun getAllUser(): Flow<List<UserEntity>>

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()

    @Query("SELECT EXISTS(SELECT * FROM users WHERE username = :username)")
    suspend fun isUserExist(username: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Query("DELETE FROM users WHERE is_favorite = 0")
    suspend fun deleteAllNonFavorites()

    @Query("UPDATE users SET is_favorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteState(id: Int, isFavorite: Boolean)

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getUserById(id : Int) : UserEntity?

}