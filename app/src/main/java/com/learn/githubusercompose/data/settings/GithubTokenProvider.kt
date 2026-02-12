package com.learn.githubusercompose.data.settings

import com.learn.githubusercompose.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubTokenProvider @Inject constructor(
    private val repository: SettingsRepository
) {

    @Volatile
    private var cachedToken: String? = null

    init {
        CoroutineScope(Dispatchers.IO).launch {
            repository.githubToken.collect { token ->
                cachedToken = token
            }
        }
    }

    fun getTokenBlocking(): String? {
        return cachedToken ?: runBlocking {
            repository.githubToken.first()
        }
    }
}