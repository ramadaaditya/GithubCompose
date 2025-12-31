package com.learn.githubusercompose.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.learn.githubusercompose.domain.model.FakeUserDataSource
import com.learn.githubusercompose.domain.model.User
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun FollowerFollowingTabLayout(modifier: Modifier = Modifier) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Follower", "Following")
    val pagerState = rememberPagerState(pageCount = { tabTitles.size })

    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = modifier
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
                .weight(1f),
        ) { page ->
            when (page) {
                0 -> FollowerList()
                1 -> FollowingList()
            }
        }
    }

    // Update selectedTabIndex when pagerState changes
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            selectedTabIndex = page
        }
    }
}

@Composable
fun FollowerList(
    modifier: Modifier = Modifier,
    user: List<User> = FakeUserDataSource.dummyFollow
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
    ) {
        items(user) { data ->
//            UserItem(
//                image = data.avatarUrl,
//                login = data.login,
//                userRepo = data.repoCount,
//            )
        }
    }
}

@Composable
fun FollowingList(
    modifier: Modifier = Modifier,
    user: List<User> = FakeUserDataSource.dummyFollow
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
    ) {
        items(user) { data ->
//            UserItem(
//                model = data.avatarUrl,
//                login = data.login,
//                userRepo = data.repoCount,
//            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun FollowerFollowingTabLayoutPreview() {
    FollowerFollowingTabLayout()
}