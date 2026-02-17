package com.learn.githubusercompose.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.githubusercompose.core.common.UiState
import com.learn.githubusercompose.core.common.UiState.Success
import com.learn.githubusercompose.domain.model.Result
import com.learn.githubusercompose.data.repository.TrendingRepository
import com.learn.githubusercompose.data.repository.UserRepository
import com.learn.githubusercompose.domain.model.TrendingRepo
import com.learn.githubusercompose.domain.model.User
import com.learn.githubusercompose.domain.repository.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


data class HomeUiState(
    val users: List<User> = emptyList(),
    val trendingRepo: List<TrendingRepo>? = emptyList(),
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: IUserRepository,
    private val trendingRepository: TrendingRepository
) : ViewModel() {
    private val _searchState = MutableStateFlow<UiState<Unit>>(Success(Unit))

    val uiState: StateFlow<UiState<HomeUiState>> = combine(
        trendingRepository.getTrendingRepositories(query = "Kotlin"),
        userRepository.getUsersStream(),
        _searchState
    ) { trendingState, users, searchState ->

        if (searchState is UiState.Loading) {
            return@combine UiState.Loading
        }
        if (searchState is UiState.Error) {
            return@combine UiState.Error(searchState.errorMessage)
        }
        when (trendingState) {
            is Result.Error -> UiState.Error(trendingState.message ?: "Unknown message")
            is Result.Loading -> UiState.Loading
            is Result.Success -> {
                val combineData = HomeUiState(
                    users = users,
                    trendingRepo = trendingState.data
                )
                Success(combineData)

            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UiState.Loading
    )


    fun searchUsers(newQuery: String) {
        viewModelScope.launch {
            userRepository.searchUsers(newQuery)
                .collect { result ->
                    when (result) {
                        is Result.Error -> {
                            _searchState.value = UiState.Error(result.message ?: "Unknown error")
                        }

                        is Result.Loading -> {
                            _searchState.value = UiState.Loading
                        }


                        is Result.Success -> {
                            _searchState.value = Success(Unit)
                        }
                    }
                }
        }
    }
}