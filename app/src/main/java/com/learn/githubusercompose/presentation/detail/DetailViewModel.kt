package com.learn.githubusercompose.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.learn.githubusercompose.core.common.UiState
import com.learn.githubusercompose.core.navigation.ScreenRoute
import com.learn.githubusercompose.domain.model.DetailUser
import com.learn.githubusercompose.domain.model.Result
import com.learn.githubusercompose.domain.model.User
import com.learn.githubusercompose.domain.repository.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DetailUiState(
    val id: Int,
    val username: String,
    val image: String,
    val name: String,
    val bio: String,
    val follower: Int,
    val following: Int,
    val repoCount: Int,
)

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: IUserRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val route = savedStateHandle.toRoute<ScreenRoute.DetailUserRoute>()
    private val usernameArg = route.username

    val isFavorite: StateFlow<Boolean> =
        repository.getUsersStream()
            .map { users ->
                users.firstOrNull() { it.username == usernameArg }?.isFavorite ?: false

            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = false
            )


    fun toggleFavorite(user: User) {
        viewModelScope.launch {
            if (isFavorite.value) {
                repository.deleteFavorite(user.id)
            } else {
                repository.insertFavorite(user)
            }
        }
    }

    val uiState: StateFlow<UiState<DetailUiState>> =
        repository.getDetailUser(usernameArg)
            .map {
                it.toDetailUiState()
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = UiState.Loading
            )

    val follower: StateFlow<UiState<List<User>>> =
        repository.getFollowers(usernameArg)
            .map { resource ->

                resource.toListUiState()
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = UiState.Loading
            )

    val following: StateFlow<UiState<List<User>>> =
        repository.getFollowing(usernameArg)
            .map { resource ->
                resource.toListUiState()
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = UiState.Loading
            )
}


private fun Result<DetailUser?>.toDetailUiState(): UiState<DetailUiState> {
    return when (this) {
        is Result.Loading -> UiState.Loading
        is Result.Error -> UiState.Error(
            errorMessage = message ?: "Unknown error occurred"
        )

        is Result.Success -> {
            val user = data
            if (user != null) {
                UiState.Success(
                    DetailUiState(
                        image = user.avatarUrl,
                        name = user.name,
                        bio = user.bio,
                        follower = user.followers,
                        following = user.following,
                        repoCount = user.repoCount,
                        id = user.id,
                        username = user.username,
                    )
                )
            } else {
                UiState.Error("User data not found")
            }
        }
    }
}

private fun Result<List<User>>.toListUiState(): UiState<List<User>> {
    return when (this) {
        is Result.Loading -> UiState.Loading
        is Result.Error -> UiState.Error(
            errorMessage = message ?: "Unknown error occurred"
        )

        is Result.Success -> UiState.Success(data)
    }
}

