package com.learn.githubusercompose.presentation.detail

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.learn.githubusercompose.R
import com.learn.githubusercompose.core.common.UiState
import com.learn.githubusercompose.domain.model.User
import com.learn.githubusercompose.ui.GithubIcons
import com.learn.githubusercompose.ui.components.FollowerFollowingTabLayout


@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    val detailState by viewModel.uiState.collectAsStateWithLifecycle()
    val followerState by viewModel.follower.collectAsStateWithLifecycle()
    val followingState by viewModel.following.collectAsStateWithLifecycle()
    val isFavorite by viewModel.isFavorite.collectAsStateWithLifecycle()
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {


        when (val state = detailState) {
            is UiState.Error -> {
                Text(text = state.errorMessage)
            }

            UiState.Loading -> {
                CircularProgressIndicator()
            }

            is UiState.Success -> {
                DetailContent(
                    detailUser = state.data,
                    followerState = followerState,
                    followingState = followingState,
                    onBackClick = navigateBack,
                    onFavoriteClick = {
                        viewModel.toggleFavorite(state.data.toUser())
                    },
                    isFavorite = isFavorite
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
    followerState: UiState<List<User>>,
    followingState: UiState<List<User>>,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    isFavorite: Boolean
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        MyTopBar(title = "Details", onBackClick = { onBackClick() })
        Column(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                AsyncImage(
                    model = detailUser.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    error = painterResource(R.drawable.logo),
                    modifier = Modifier
                        .matchParentSize()
                        .clip(CircleShape)
                )

                IconButton(
                    onClick = {
                        onFavoriteClick()
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = if (isFavorite) GithubIcons.selectedFavorite else GithubIcons.unselectedFavorite,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }

            }

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
            modifier = Modifier.weight(1f),
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

//private val previewDetailUser = DetailUiState(
//    image = "https://avatars.githubusercontent.com/u/1?v=4",
//    name = "Octocat",
//    bio = "GitHub mascot 👋",
//    follower = 120,
//    following = 80,
//    repoCount = 42
//)

private val previewUserList = listOf(
    User(
        id = 1,
        username = "john_doe",
        avatarUrl = "https://avatars.githubusercontent.com/u/2?v=4"
    ),
    User(
        id = 2,
        username = "jane_doe",
        avatarUrl = "https://avatars.githubusercontent.com/u/3?v=4"
    ),
    User(
        id = 3,
        username = "compose_dev",
        avatarUrl = "https://avatars.githubusercontent.com/u/4?v=4"
    )
)

fun DetailUiState.toUser(): User {
    return User(
        username = username,
        avatarUrl = image,
        isFavorite = false,
        id = id,
    )
}

//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//@androidx.compose.ui.tooling.preview.Preview(
//    showBackground = true,
//    device = Devices.PIXEL_7
//)
//fun DetailContentPreview_Success() {
//    MaterialTheme {
//        DetailContent(
//            detailUser = previewDetailUser,
//            followerState = UiState.Success(previewUserList),
//            followingState = UiState.Success(previewUserList),
//            onBackClick = {}
//        )
//    }
//}
//
//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
//fun DetailContentPreview_Loading() {
//    MaterialTheme {
//        DetailContent(
//            detailUser = previewDetailUser,
//            followerState = UiState.Loading,
//            followingState = UiState.Loading,
//            onBackClick = {}
//        )
//    }
//}
//
//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
//fun DetailContentPreview_Error() {
//    MaterialTheme {
//        DetailContent(
//            detailUser = previewDetailUser,
//            followerState = UiState.Error("Failed to load followers"),
//            followingState = UiState.Error("Failed to load following"),
//            onBackClick = {}
//        )
//    }
//}
