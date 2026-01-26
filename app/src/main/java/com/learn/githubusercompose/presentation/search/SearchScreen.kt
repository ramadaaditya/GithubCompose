//package com.learn.githubusercompose.presentation.search
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import com.learn.githubusercompose.domain.model.User
//import com.learn.githubusercompose.presentation.home.ActivityItem
//import com.learn.githubusercompose.presentation.home.EmptyState
//import com.learn.githubusercompose.presentation.home.HomeDashboardContent
//import com.learn.githubusercompose.presentation.home.HomeViewModel
//import com.learn.githubusercompose.presentation.home.SectionHeader
//import com.learn.githubusercompose.presentation.home.TrendingRepoCard
//import com.learn.githubusercompose.ui.common.UiState
//import com.learn.githubusercompose.ui.components.Search
//
//@Composable
//fun SearchScreen(
//    viewModel: HomeViewModel = hiltViewModel()
//) {
//    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
//    var query by rememberSaveable { mutableStateOf("") }
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Search(
//            query = query,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp, vertical = 8.dp),
//            onQueryChange = { newQuery ->
////                query = newQuery
////                viewModel.searchUsers(newQuery)
//            },
//            onSearch = {
//                viewModel.searchUsers(query)
//            }
//        )
//
//        Box(
//            Modifier
//                .fillMaxWidth()
//                .weight(1f),
//            contentAlignment = Alignment.Center,
//        ) {
//            when (val state = uiState) {
//                is UiState.Error -> {
//                    Text(
//                        text = state.errorMessage,
//                        color = MaterialTheme.colorScheme.error,
//                        modifier = Modifier.padding(16.dp)
//                    )
//                }
//
//                is UiState.Loading -> {
//                    CircularProgressIndicator()
//                }
//
//                is UiState.Success -> {
//                    if (state.data.isEmpty()) {
//                        EmptyState()
//                    } else {
////                        HomeDashboardContent(
////                            userList = state.data,
////                            onItemClick = {
//////                            navigateToDetail(it.id)
////                            }
////                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
////
////@Composable
////private fun HomeDashboardContent(
////    userList: List<User>,
////    onItemClick: () -> Unit,
////    modifier: Modifier = Modifier
////) {
////    LazyColumn(
////        contentPadding = PaddingValues(bottom = 24.dp),
////        modifier = modifier
////            .fillMaxSize(),
////        verticalArrangement = Arrangement.spacedBy(16.dp)
////    ) {
////        item {
////            SectionHeader(
////                title = "Trending Repositories",
////                actionText = "See All",
////                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
////            )
////        }
////
////        item {
////            LazyRow(
////                contentPadding = PaddingValues(horizontal = 16.dp),
////                horizontalArrangement = Arrangement.spacedBy(12.dp)
////            ) {
////                // Simulasi data trending menggunakan data user
////                items(userList.take(5)) { user ->
////                    TrendingRepoCard(trendingRepo = t)
////                }
////            }
////        }
////        item {
////            SectionHeader(
////                title = "Activity Feed",
////                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
////            )
////        }
////        items(
////            items = userList, key = { user -> user.id }
////        ) { data ->
////            ActivityItem(
////                user = data,
////                modifier = Modifier
////                    .padding(horizontal = 16.dp)
//////                    .clickable { }
////            )
////        }
////    }
////}