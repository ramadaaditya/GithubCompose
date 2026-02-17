package com.learn.githubusercompose.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(name = "username")
    val username: String,
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String?,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false
)
