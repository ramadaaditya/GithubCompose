package com.learn.githubusercompose.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.learn.githubusercompose.core.common.ErrorType
import com.learn.githubusercompose.core.common.UiState
import com.learn.githubusercompose.core.utils.languageColor
import com.learn.githubusercompose.domain.model.TrendingRepo
import com.learn.githubusercompose.domain.model.User
import com.learn.githubusercompose.ui.components.EmptyState
import com.learn.githubusercompose.ui.components.Search
import com.learn.githubusercompose.ui.components.SearchUserItem


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToDetail: (String) -> Unit,
    innerPadding: PaddingValues
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var query by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Search(
            query = query,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            onQueryChange = { newQuery ->
                query = newQuery
            },
            onSearch = {
                viewModel.searchUsers(query)
            }
        )

        Box(
            Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            when (val state = uiState) {
                is UiState.Error -> {
                    when (state.errorType) {
                        ErrorType.UNAUTHORIZED -> {
                            Text(
                                text = "Github Tokennya engga valid bang",
                                color = MaterialTheme.colorScheme.error,
                            )
                        }

                        else -> {
                            Text(
                                text = state.errorMessage,
                                color = MaterialTheme.colorScheme.error,
                            )
                        }
                    }
                }

                is UiState.Loading -> {
                    CircularProgressIndicator()
                }

                is UiState.Success -> {
                    HomeDashboardContent(
                        userList = state.data.users,
                        trendingList = state.data.trendingRepo ?: emptyList(),
                        onItemClick = { username ->
                            navigateToDetail(username)
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun HomeDashboardContent(
    trendingList: List<TrendingRepo>,
    userList: List<User>,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            SectionHeader(
                title = "Trending Repositories",
                actionText = "See All",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(trendingList.take(5)) { user ->
                    TrendingRepoCard(trendingRepo = user)
                }
            }
        }

        if (userList.isEmpty()) {
            item {
                EmptyState(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp)
                )
            }
        } else {
            items(
                items = userList, key = { user -> user.id }
            ) { data ->
                SearchUserItem(
                    state = data,
                    onItemClick = { username ->
                        onItemClick(username)
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
fun SectionHeader(
    modifier: Modifier = Modifier,
    title: String,
    actionText: String? = null
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground

            )
        )
        if (actionText != null) {
            Text(
                text = actionText,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.primary
                ),
            )
        }
    }
}


@Composable
fun TrendingRepoCard(trendingRepo: TrendingRepo, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = modifier
            .width(280.dp)
            .height(140.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = trendingRepo.ownerAvatarUrl,
                        contentDescription = null,
                        modifier = Modifier.padding(4.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "${trendingRepo.ownerName}/${trendingRepo.name}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(
                text = trendingRepo.description,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(languageColor(trendingRepo.language))
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = trendingRepo.language,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    Icons.Default.Star,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(14.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = trendingRepo.stars.toString(),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        }
    }
}