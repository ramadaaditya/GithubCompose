package com.learn.githubusercompose.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.learn.githubusercompose.R
import com.learn.githubusercompose.di.Injection
import com.learn.githubusercompose.model.User
import com.learn.githubusercompose.ui.ViewModelFactory
import com.learn.githubusercompose.ui.common.UiState
import com.learn.githubusercompose.ui.components.ErrorScreen
import com.learn.githubusercompose.ui.components.Search
import com.learn.githubusercompose.ui.components.UserItem

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
            }

            is UiState.Success -> {
                HomeContent(
                    user = uiState.data,
                    navigateToDetail = navigateToDetail,
                    onSearch = { query -> viewModel.searchUsers(query) }
                )
            }

            is UiState.Error -> {
                ErrorScreen(
                    errorMessage = uiState.errorMessage,
                    onRetry = {
                        viewModel.getAllUsers()
                    },
                    modifier = modifier
                )
            }
        }
    }
}


@Composable
fun HomeContent(
    user: List<User>,
    navigateToDetail: (Long) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column {
        Search(
            modifier = Modifier.fillMaxWidth(),
            onQueryChange = { query ->
                onSearch(query)
            }
        )
        if (user.isEmpty()) {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = stringResource(R.string.user_not_found))
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                modifier = modifier
            ) {
                items(user) { data ->
                    UserItem(
                        image = data.avatarUrl,
                        login = data.login,
                        userRepo = data.repoCount,
                        modifier = Modifier.clickable {
                            navigateToDetail(data.id)
                        }
                    )
                }
            }
        }
    }
}
