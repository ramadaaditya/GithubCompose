package com.learn.githubusercompose.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trending_repo")
data class TrendingRepoEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "full_name")
    val fullName: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "owner_name")
    val ownerName: String,
    @ColumnInfo(name = "owner_avatar_url")
    val ownerAvatarUrl: String,
    @ColumnInfo(name = "stars")
    val stars: Int,
    @ColumnInfo(name = "language")
    val language: String,
    @ColumnInfo(name = "fork_count")
    val forkCount: Int
)