package com.learn.githubusercompose.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.learn.githubusercompose.data.local.dao.SearchUserDao
import com.learn.githubusercompose.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class UserDatabase : RoomDatabase() {
    abstract fun searchDao(): SearchUserDao
}