package com.learn.githubusercompose.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.learn.githubusercompose.core.common.UiState
import com.learn.githubusercompose.domain.model.UserItemUiState
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun FollowerFollowingTabLayout(
    modifier : Modifier = Modifier,
    followerState: UiState<List<UserItemUiState>>,
    followingState: UiState<List<UserItemUiState>>
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Follower", "Following")
    val pagerState = rememberPagerState(pageCount = { tabTitles.size })

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(text = title) }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.Top,
        ) { page ->
            when (page) {
                0 -> FollowerList(state = followerState)
                1 -> FollowingList(state = followingState)
            }
        }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            selectedTabIndex = page
        }
    }
}

@Composable
fun FollowerList(
    state: UiState<List<UserItemUiState>>
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        when (state) {
            is UiState.Error -> {
                Text(text = "Error occurred")
            }

            is UiState.Loading -> {
                CircularProgressIndicator()
            }

            is UiState.Success -> {
                val users = state.data
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                ) {
                    items(users) { data ->
                        SearchUserItem(
                            state = data,
                            onItemClick = {}
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun FollowingList(
    state: UiState<List<UserItemUiState>>
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        when (state) {
            is UiState.Error -> {
                Text(text = "Error occurred")
            }

            UiState.Loading -> {
                CircularProgressIndicator()
            }

            is UiState.Success -> {
                val users = state.data
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                ) {
                    items(users) { data ->
                        SearchUserItem(
                            state = data,
                            onItemClick = {}
                        )
                    }
                }
            }
        }
    }
}
