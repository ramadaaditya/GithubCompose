package com.learn.githubusercompose.presentation.detail

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RepositoryDetailScreen(
    modifier: Modifier = Modifier,
    onBackClick : () -> Unit,
) {
    Scaffold(
        topBar = {
            RepoTopBar()
        },
    ) { }

}

@Composable
fun RepoTopBar() {
    TODO("Not yet implemented")
}