package com.learn.githubusercompose.presentation.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import coil3.compose.AsyncImage
import com.learn.githubusercompose.R
import com.learn.githubusercompose.core.common.UiState
import com.learn.githubusercompose.core.navigation.ScreenRoute
import com.learn.githubusercompose.domain.model.UserItemUiState
import com.learn.githubusercompose.ui.components.FollowerFollowingTabLayout

fun NavController.navigateToDetail(navOptions: NavOptions? = null) =
    if (navOptions != null) {
        navigate(route = ScreenRoute.DetailUserRoute, navOptions)
    } else {
        navigate(ScreenRoute.DetailUserRoute)
    }


@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    val detailState = viewModel.uiState.collectAsStateWithLifecycle().value
    val followerState = viewModel.follower.collectAsStateWithLifecycle().value
    val followingState = viewModel.following.collectAsStateWithLifecycle().value
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (detailState) {
            is UiState.Error -> {
                Text(text = detailState.errorMessage)
            }

            UiState.Loading -> {
                CircularProgressIndicator()
            }

            is UiState.Success -> {
                DetailContent(
                    detailUser = detailState.data,
                    followerState = followerState,
                    followingState = followingState,
                    onBackClick = navigateBack
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    detailUser: DetailUiState,
    followerState: UiState<List<UserItemUiState>>,
    followingState: UiState<List<UserItemUiState>>,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        MyTopBar(title = "Details", onBackClick = { onBackClick() })
        Column(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                model = detailUser.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.logo),
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = detailUser.name,
                fontSize = 20.sp,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = detailUser.bio,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = detailUser.repoCount.toString(),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = "Repository"
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = detailUser.follower.toString(),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = "Follower"
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = detailUser.following.toString(),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = "Following"
                    )
                }
            }
        }
        FollowerFollowingTabLayout(
            followerState = followerState,
            followingState = followingState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(
    title: String,
    onBackClick: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 20.sp,
                style = MaterialTheme.typography.titleMedium
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
    )
}