package com.learn.githubusercompose.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.githubusercompose.data.UserRepository
import com.learn.githubusercompose.model.User
import com.learn.githubusercompose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: UserRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<User>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<User>>> get() = _uiState

    init {
        getAllUsers()
    }

    fun getAllUsers() {
        viewModelScope.launch {
            try {
                repository.getUsers().collect { users ->
                    _uiState.value = UiState.Success(users)
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    fun searchUsers(newQuery: String) {
        viewModelScope.launch {
            try {
                val searchResult = repository.searchUsers(newQuery)
                _uiState.value = UiState.Success(searchResult)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }
}