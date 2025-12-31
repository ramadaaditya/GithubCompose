package com.learn.githubusercompose.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.learn.githubusercompose.data.local.entity.UserEntity

@Dao
interface SearchUserDao {
    @Query("SELECT * FROM users")
    fun getAllUser(): PagingSource<Int, UserEntity>
}