package com.learn.githubusercompose.domain.model

data class TrendingRepo(
    val id: Int,
    val name: String,
    val fullName: String,
    val description: String,
    val ownerName: String,
    val ownerAvatarUrl: String,
    val stars: Int,
    val language: String,
    val forkCount: Int
)
