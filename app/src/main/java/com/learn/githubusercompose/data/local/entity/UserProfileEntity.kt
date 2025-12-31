package com.learn.githubusercompose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: Int,
    val login: String,
    val avatarUrl: String = "",
    val type: String,
    val isFavorite: Boolean = true
)