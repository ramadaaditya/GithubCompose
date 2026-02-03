package com.learn.githubusercompose.domain.model

data class DetailUser(
    val following: Int,
    val followers: Int,
    val name: String,
    val username: String,
    val repoCount: Int,
    val bio: String,
    val email: String,
    val location: String,
    val avatarUrl: String,
    val id: Int,
)