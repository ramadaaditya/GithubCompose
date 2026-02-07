package com.learn.githubusercompose.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.learn.githubusercompose.core.common.UiState
import com.learn.githubusercompose.core.navigation.ScreenRoute
import com.learn.githubusercompose.data.Resource
import com.learn.githubusercompose.domain.model.DetailUser
import com.learn.githubusercompose.domain.model.UserItemUiState
import com.learn.githubusercompose.domain.repository.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class DetailUiState(
    val image: String,
    val name: String,
    val bio: String,
    val follower: Int,
    val following: Int,
    val repoCount: Int,
)

@HiltViewModel
class DetailViewModel @Inject constructor(
    repository: IUserRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val route = savedStateHandle.toRoute<ScreenRoute.DetailUserRoute>()
    private val usernameArg = route.username

    val uiState: StateFlow<UiState<DetailUiState>> =
        repository.getDetailUser(usernameArg)
            .map { it.toDetailUiState() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = UiState.Loading
            )

    val follower: StateFlow<UiState<List<UserItemUiState>>> =
        repository.getFollowers(usernameArg)
            .map { resource ->

                resource.toListUiState()
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = UiState.Loading
            )

    val following: StateFlow<UiState<List<UserItemUiState>>> =
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


private fun Resource<DetailUser>.toDetailUiState(): UiState<DetailUiState> {
    return when (this) {
        is Resource.Loading -> UiState.Loading
        is Resource.Error -> UiState.Error(
            errorMessage = message ?: "Unknown error occurred"
        )

        is Resource.Success -> {
            data?.let { user ->
                UiState.Success(
                    DetailUiState(
                        image = user.avatarUrl,
                        name = user.name,
                        bio = user.bio,
                        follower = user.followers,
                        following = user.following,
                        repoCount = user.repoCount
                    )
                )
            } ?: UiState.Error("User data is empty")
        }
    }
}

private fun Resource<List<UserItemUiState>>.toListUiState(): UiState<List<UserItemUiState>> {
    return when (this) {
        is Resource.Loading -> UiState.Loading
        is Resource.Error -> UiState.Error(
            errorMessage = message ?: "Unknown error occurred"
        )

        is Resource.Success -> UiState.Success(data ?: emptyList())
    }
}

