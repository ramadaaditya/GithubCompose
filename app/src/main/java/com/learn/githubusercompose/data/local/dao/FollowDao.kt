package com.learn.githubusercompose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.learn.githubusercompose.data.local.entity.FollowerEntity
import com.learn.githubusercompose.data.local.entity.FollowingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FollowDao {
    @Query("SELECT * FROM follower WHERE username = :username ORDER BY id ASC")
    fun getFollowers(username: String): Flow<List<FollowerEntity>>

    @Insert(onConflict = REPLACE)
    suspend fun insertFollowers(following: List<FollowerEntity>)

    @Query("SELECT * FROM `following` WHERE username = :username ORDER BY id ASC")
    fun getFollowing(username: String): Flow<List<FollowingEntity>>

    @Insert(onConflict = REPLACE)
    suspend fun insertFollowing(following: List<FollowingEntity>)

    @Query("DELETE FROM follower WHERE username = :username")
    suspend fun deleteFollowersByUsername(username  : String)

}