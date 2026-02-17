package com.learn.githubusercompose.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.githubusercompose.core.common.UiState
import com.learn.githubusercompose.data.repository.UserRepository
import com.learn.githubusercompose.domain.model.Result
import com.learn.githubusercompose.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    val uiState: StateFlow<UiState<List<User>>> =
        repository.getAllFavorites()
            .map { result ->
                when (result) {
                    is Result.Success -> UiState.Success(result.data)
                    is Result.Error -> UiState.Error(result.message ?: "Unknown error")
                    is Result.Loading -> UiState.Loading
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = UiState.Loading
            )

    fun removeFavorite(user: User) {
        viewModelScope.launch {
            repository.deleteFavorite(user.id)
        }
    }
}