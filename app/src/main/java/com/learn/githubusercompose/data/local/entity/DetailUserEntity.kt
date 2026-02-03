package com.learn.githubusercompose.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detail_user")
data class DetailUserEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo("following")
    val following: Int,
    @ColumnInfo("followers")
    val followers: Int,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("username")
    val username: String,
    @ColumnInfo("repo_count")
    val repoCount: Int,
    @ColumnInfo("bio")
    val bio: String,
    @ColumnInfo("email")
    val email: String,
    @ColumnInfo("location")
    val location: String,
    @ColumnInfo("avatar_url")
    val avatarUrl: String,
)