package com.learn.githubusercompose.presentation.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.learn.githubusercompose.core.common.UiState
import com.learn.githubusercompose.core.navigation.ScreenRoute
import com.learn.githubusercompose.domain.model.User
import com.learn.githubusercompose.ui.components.SearchUserItem
import com.learn.githubusercompose.ui.theme.GithubUserComposeTheme

fun NavController.navigateToFavorite(navOptions: NavOptions? = null) =
    if (navOptions != null) {
        navigate(route = ScreenRoute.FavoriteRoute, navOptions)
    } else {
        navigate(ScreenRoute.FavoriteRoute)
    }


@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = hiltViewModel(),
    onClick: (User) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        when (val state = uiState) {
            is UiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                )
            }

            is UiState.Error -> {
                Text(
                    text = state.errorMessage,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is UiState.Success -> {
                if (state.data.isEmpty()) {
                    EmptyFavoriteState()
                } else {
                    FavoriteContent(
                        user = state.data,
                        onFavoriteClick = { user ->
                            viewModel.removeFavorite(user)
                        },
                        onItemClick = onClick
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteContent(
    user: List<User>,
    modifier: Modifier = Modifier,
    onFavoriteClick: (User) -> Unit,
    onItemClick: (User) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 24.dp),
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = user, key = { user -> user.id }
        ) { data ->
            SearchUserItem(
                state = data,
                onItemClick = { onItemClick(data) },
                displayFavorite = true,
                onFavoriteClick = onFavoriteClick
            )
        }
    }
}

@Composable
private fun EmptyFavoriteState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Belum ada favorite")
    }
}


@Preview(showBackground = true)
@Composable
private fun FavoriteScreenPreview() {
    val listFavoriteUser = listOf(
        User(
            id = 12983,
            username = "Ramada",
            avatarUrl = "Error",
            isFavorite = true,
        ),
        User(
            id = 12984,
            username = "Aditya",
            avatarUrl = "Error",
            isFavorite = true,
        ),
        User(
            id = 12985,
            username = "Muhammad",
            avatarUrl = "Error",
            isFavorite = true,
        ),
        User(
            id = 12986,
            username = "Junior",
            avatarUrl = "Error",
            isFavorite = false,
        ),
    )
    GithubUserComposeTheme() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            FavoriteContent(
                user = listFavoriteUser,
                onFavoriteClick = {},
                onItemClick = {}
            )
        }
    }
}