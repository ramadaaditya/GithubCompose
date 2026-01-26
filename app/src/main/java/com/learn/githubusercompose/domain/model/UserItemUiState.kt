package com.learn.githubusercompose.domain.model

data class UserItemUiState(
    val id: Int,
    val username: String,
    val avatarUrl: String,
    val isFavorite: Boolean = false,
)