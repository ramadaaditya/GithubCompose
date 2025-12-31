package com.learn.githubusercompose.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.githubusercompose.data.UserRepository
import com.learn.githubusercompose.data.remote.response.ItemsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<List<ItemsItem>>(emptyList())
    val uiState: StateFlow<List<ItemsItem>> = _uiState
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        searchUsers("Ramada")
    }

    fun searchUsers(newQuery: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _uiState.value = repository.searchUsers(newQuery)
            } catch (e: Exception) {
                _uiState.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}