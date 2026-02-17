package com.learn.githubusercompose.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_cache")
data class SearchCacheEntity(
    @PrimaryKey
    @ColumnInfo(name = "query")
    val query: String,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "total_count")
    val totalCount: Int
)

@Entity(
    tableName = "search_user_cache",
    primaryKeys = ["query", "username"]
)
data class SearchUserCacheEntity(
    @ColumnInfo(name = "query")
    val query: String,

    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,

    @ColumnInfo(name = "html_url")
    val htmlUrl: String,

    @ColumnInfo(name = "position")
    val position: Int
)