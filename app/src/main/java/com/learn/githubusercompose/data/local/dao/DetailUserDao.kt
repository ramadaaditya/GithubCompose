package com.learn.githubusercompose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.learn.githubusercompose.data.local.entity.DetailUserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DetailUserDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertDetailUser(detailUser: DetailUserEntity)

    @Query("SELECT * FROM detail_user WHERE username = :username")
    fun getDetailUser(username: String): Flow<DetailUserEntity?>

}