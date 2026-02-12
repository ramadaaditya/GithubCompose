package com.learn.githubusercompose.data.settings

import com.learn.githubusercompose.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    private val secureTokenDataStore: SecureTokenDataStore
) : SettingsRepository {
    override val githubToken: Flow<String?> =
        secureTokenDataStore.githubToken

    override suspend fun saveGithubToken(token: String) {
        secureTokenDataStore.saveToken(token)

    }

    override suspend fun clearGithubToken() {
        secureTokenDataStore.clearToken()
    }

}