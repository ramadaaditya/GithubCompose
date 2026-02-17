package com.learn.githubusercompose.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorite_users",
    indices = [Index(value = ["username"], unique = true)]
)
data class FavoriteUserEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(name = "username")
    val username: String,
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,
    @ColumnInfo(name = "added_at")
    val addedAt: Long = System.currentTimeMillis()
)