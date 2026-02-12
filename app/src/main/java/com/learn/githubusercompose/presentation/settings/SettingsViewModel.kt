package com.learn.githubusercompose.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.githubusercompose.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {

    val githubToken: StateFlow<String?> =
        repository.githubToken.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            null
        )

    fun saveGithubToken(token: String) {
        viewModelScope.launch {
            repository.saveGithubToken(
                token
            )
        }
    }

    fun clearGithubToken() {
        viewModelScope.launch {
            repository.clearGithubToken()
        }
    }
}