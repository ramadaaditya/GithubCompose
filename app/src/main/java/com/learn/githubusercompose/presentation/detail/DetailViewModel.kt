package com.learn.githubusercompose.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.githubusercompose.data.UserRepository
import com.learn.githubusercompose.domain.model.User
import com.learn.githubusercompose.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<User>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<User>>
        get() = _uiState

    fun getUserById(userId: Long) {
//        viewModelScope.launch {
//            _uiState.value = UiState.Loading
//            _uiState.value = UiState.Success(repository.getUserById(userId))
//        }
    }
}