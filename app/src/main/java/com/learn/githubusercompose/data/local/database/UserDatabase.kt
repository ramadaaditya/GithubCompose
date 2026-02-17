package com.learn.githubusercompose.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.learn.githubusercompose.data.local.dao.DetailUserDao
import com.learn.githubusercompose.data.local.dao.FavoriteUserDao
import com.learn.githubusercompose.data.local.dao.FollowDao
import com.learn.githubusercompose.data.local.dao.UserDao
import com.learn.githubusercompose.data.local.dao.TrendingRepoDao
import com.learn.githubusercompose.data.local.entity.DetailUserEntity
import com.learn.githubusercompose.data.local.entity.FavoriteUserEntity
import com.learn.githubusercompose.data.local.entity.FollowerEntity
import com.learn.githubusercompose.data.local.entity.FollowingEntity
import com.learn.githubusercompose.data.local.entity.UserEntity
import com.learn.githubusercompose.data.local.entity.TrendingRepoEntity

@Database(
    entities = [
        UserEntity::class,
        TrendingRepoEntity::class,
        DetailUserEntity::class,
        FollowingEntity::class,
        FollowerEntity::class,
        FavoriteUserEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class UserDatabase : RoomDatabase() {
    abstract fun searchDao(): UserDao
    abstract fun trendingRepoDao(): TrendingRepoDao
    abstract fun detailUserDao(): DetailUserDao
    abstract fun followDao(): FollowDao
    abstract fun favoriteUserDao(): FavoriteUserDao
}