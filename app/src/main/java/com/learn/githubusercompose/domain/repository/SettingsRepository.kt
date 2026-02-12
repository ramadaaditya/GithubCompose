package com.learn.githubusercompose.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val githubToken: Flow<String?>
    suspend fun saveGithubToken(token: String)
    suspend fun clearGithubToken()

}