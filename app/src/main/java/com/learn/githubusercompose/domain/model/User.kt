package com.learn.githubusercompose.domain.model

data class User(
    val id: Int,
    val username: String,
    val avatarUrl: String,
    val isFavorite: Boolean = false,
)