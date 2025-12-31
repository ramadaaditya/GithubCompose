package com.learn.githubusercompose.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices.PIXEL_6
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.learn.githubusercompose.R
import com.learn.githubusercompose.domain.model.FakeUserDataSource
import com.learn.githubusercompose.domain.model.User
import com.learn.githubusercompose.ui.components.Search
import com.learn.githubusercompose.ui.components.UserItem
import com.learn.githubusercompose.ui.theme.GithubUserComposeTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToDetail: (Long) -> Unit
) {
    val user by viewModel.uiState.collectAsStateWithLifecycle()
    val loading by viewModel.isLoading.collectAsStateWithLifecycle()

    Box(Modifier.fillMaxSize()) {

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = modifier
        ) {
            items(user) { data ->
                UserItem(
                    model = data.avatarUrl,
                    login = data.login,
                    userRepo = data.score ?: 0,
                    modifier = Modifier.clickable {
//                        navigateToDetail(data.id)
                    }
                )
            }
        }
        if (loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
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

        }
    }
}


@Preview(showBackground = true, device = PIXEL_6)
@Composable
private fun HomeScreenPreview() {
    GithubUserComposeTheme(darkTheme = true) {
        val dummyUser = FakeUserDataSource.dummyUser
        HomeContent(
            user = dummyUser,
            navigateToDetail = {},
            onSearch = { },
        )
    }
}