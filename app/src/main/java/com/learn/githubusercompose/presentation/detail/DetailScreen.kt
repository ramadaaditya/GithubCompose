package com.learn.githubusercompose.presentation.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.learn.githubusercompose.R
import com.learn.githubusercompose.ui.common.UiState
import com.learn.githubusercompose.ui.components.FollowerFollowingTabLayout
import com.learn.githubusercompose.ui.theme.GithubUserComposeTheme

@Composable
fun DetailScreen(
    userId: Long,
    viewModel: DetailViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getUserById(userId)
            }

            is UiState.Success -> {
                val data = uiState.data
//                DetailContent(
//                    image = data.avatarUrl,
//                    name = data.login,
//                    bio = data.bio,
//                    follower = data.follower,
//                    following = data.following,
//                    repoCount = data.repoCount,
//                    onBackClick = navigateBack
//                )
            }

            is UiState.Error -> {
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailContent(
    @DrawableRes image: Int,
    name: String,
    bio: String,
    follower: Int,
    following: Int,
    repoCount: Int,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        MyTopBar(title = "Details", onBackClick = { onBackClick() })
        Column(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp, bottom = 8.dp)
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .size(100.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally)
                    .border(2.dp, Color.Gray, CircleShape)
            )
            Text(
                text = name,
                fontSize = 20.sp,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 8.dp)
            )
            Text(
                text = bio,
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
                        text = repoCount.toString(),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = "Repository"
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = follower.toString(),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = "Follower"
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = following.toString(),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = "Following"
                    )
                }
            }
        }
        FollowerFollowingTabLayout()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
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
        modifier = modifier
    )
}


@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailScreenPreview() {
    GithubUserComposeTheme {
        DetailContent(
            R.drawable.logo,
            "Ramada Aditya",
            "Android Developer",
            100,
            100,
            20,
            onBackClick = {}
        )
    }
}