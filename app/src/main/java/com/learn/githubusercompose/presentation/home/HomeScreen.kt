package com.learn.githubusercompose.presentation.home

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.learn.githubusercompose.R
import com.learn.githubusercompose.data.local.entity.UserEntity
import com.learn.githubusercompose.ui.components.Search
import com.learn.githubusercompose.ui.components.UserItem

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToDetail: (Long) -> Unit
) {
    val uiState by viewModel.users.collectAsStateWithLifecycle()

//    Box(
//        Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        when (val state = uiState) {
//            is UiState.Error -> {
//                Text(
//                    text = state.errorMessage,
//                    color = MaterialTheme.colorScheme.error,
//                    modifier = Modifier.padding(16.dp)
//                )
//            }
//
//            is UiState.Loading -> {
//                CircularProgressIndicator()
//            }
//
//            is UiState.Success -> {
//
//                if (state.data.isEmpty()) {
//                    Text("Tidak ada data ditemukan")
//                } else {
//                    UserListContent(
//                        userList = state.data,
//                        onItemClick = {}
//                    )
//                }
//            }
//        }
//    }

    HomeContent(
        user = uiState,
        navigateToDetail = {},
        onSearch = {},
        modifier = modifier
    )
}

@Composable
fun UserListContent(
    userList: List<UserEntity>,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(
            items = userList, key = { user ->
                user.username
            }) { data ->
            UserItem(
                model = data.avatarUrl ?: "",
                login = data.username,
                userRepo = data.htmlUrl ?: "",
                modifier = Modifier.clickable {
//                        navigateToDetail(data.id)
                }
            )
        }
    }
}


@Composable
fun HomeContent(
    user: List<UserEntity>,
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
            UserListContent(
                userList = user,
                onItemClick = {}
            )

        }
    }
}


//@Preview(showBackground = true, device = PIXEL_6)
//@Composable
//private fun HomeScreenPreview() {
//    GithubUserComposeTheme(darkTheme = true) {
//        val dummyUser = FakeUserDataSource.dummyUser
//        HomeContent(
//            user = dummyUser,
//            navigateToDetail = {},
//            onSearch = { },
//        )
//    }
//}