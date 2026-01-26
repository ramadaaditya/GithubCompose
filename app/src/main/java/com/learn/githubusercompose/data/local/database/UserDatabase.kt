package com.learn.githubusercompose.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.learn.githubusercompose.data.local.dao.SearchUserDao
import com.learn.githubusercompose.data.local.dao.TrendingRepoDao
import com.learn.githubusercompose.data.local.entity.SearchUserEntity
import com.learn.githubusercompose.data.local.entity.TrendingRepoEntity

@Database(
    entities = [SearchUserEntity::class, TrendingRepoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class UserDatabase : RoomDatabase() {
    abstract fun searchDao(): SearchUserDao
    abstract fun trendingRepoDao() : TrendingRepoDao
}