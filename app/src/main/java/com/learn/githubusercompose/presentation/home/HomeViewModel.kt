package com.learn.githubusercompose.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.githubusercompose.data.UserRepository
import com.learn.githubusercompose.data.local.entity.UserEntity
import com.learn.githubusercompose.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    val users : StateFlow<List<UserEntity>> = repository.getUsersStream()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    private val _uiState = MutableStateFlow<UiState<List<UserEntity>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<UserEntity>>> = _uiState

    fun searchUsers(newQuery: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.searchUsers(newQuery)
                .collect {result ->
                    result.onSuccess {data ->
                        _uiState.value = UiState.Success(data)
                    }
                    result.onFailure { error->
                        _uiState.value = UiState.Error(error.message ?: "Terjadi kesalahan")
                    }
                }
        }
    }
}