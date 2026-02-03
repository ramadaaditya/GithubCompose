package com.learn.githubusercompose.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.learn.githubusercompose.core.common.UiState
import com.learn.githubusercompose.core.navigation.ScreenRoute
import com.learn.githubusercompose.data.Resource
import com.learn.githubusercompose.domain.model.UserItemUiState
import com.learn.githubusercompose.domain.repository.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
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
    private val repository: IUserRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val route = savedStateHandle.toRoute<ScreenRoute.DetailUserRoute>()
    val usernameArg = route.username

    private val _uiState = MutableStateFlow<UiState<DetailUiState>>(UiState.Loading)
    val uiState: StateFlow<UiState<DetailUiState>>
        get() = _uiState

    //    private val _follower = MutableStateFlow<UiState<List<UserItemUiState>>>(UiState.Loading)
    val follower: StateFlow<UiState<List<UserItemUiState>>> =
        repository.getFollowers(usernameArg)
            .map { resource ->
                when (resource) {
                    is Resource.Loading -> UiState.Loading()
                    is Resource.Error -> UiState.Error(resource.message ?: "Error")
                    is Resource.Success ->
                        UiState.Success(resource.data ?: emptyList())
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = UiState.Loading
            )

    private val _following = MutableStateFlow<UiState<List<UserItemUiState>>>(UiState.Loading)
    val following: StateFlow<UiState<List<UserItemUiState>>> get() = _following

    init {
        getUserByUsername(usernameArg)
    }


    fun getUserByUsername(username: String) {
        viewModelScope.launch {
            repository.getDetailUser(username).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _uiState.value = UiState.Error(resource.message ?: "Unknown Message")
                    }

                    is Resource.Loading -> {
                        _uiState.value = UiState.Loading
                    }

                    is Resource.Success -> {
                        val data = resource.data
                        if (data != null) {
                            val detailUiState = DetailUiState(
                                image = data.avatarUrl,
                                name = data.name,
                                bio = data.bio,
                                follower = data.followers,
                                following = data.following,
                                repoCount = data.repoCount
                            )
                            _uiState.value = UiState.Success(detailUiState)
                        }
                    }

                }
            }
        }
    }

    fun getFollowing(username: String) {
        viewModelScope.launch {
            repository.getFollowing(username).collect { resource ->
                when (resource) {
                    is Resource.Error -> _following.value =
                        UiState.Error(resource.message ?: "Error")

                    is Resource.Loading -> _following.value = UiState.Loading
                    is Resource.Success -> _following.value =
                        UiState.Success(resource.data ?: emptyList())
                }
            }
        }
    }
}
