package com.learn.githubusercompose.model

data class User(
    val id: Long,
    val login: String,
    val bio: String,
    val avatarUrl: Int,
    val repoCount: Int,
    val follower: Int,
    val following: Int,
)
