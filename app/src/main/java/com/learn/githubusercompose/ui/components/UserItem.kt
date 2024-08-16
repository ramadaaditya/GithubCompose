package com.learn.githubusercompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learn.githubusercompose.R
import com.learn.githubusercompose.ui.theme.GithubUserComposeTheme

@Composable
fun UserItem(
    image: Int,
    login: String,
    userRepo: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(68.dp)
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .border(1.dp, Color.Black, CircleShape)
                .align(Alignment.CenterVertically),
        )
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
                .fillMaxHeight()
        ) {
            Text(
                text = login,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Repos: $userRepo",
                fontSize = 15.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
    HorizontalDivider()
}


@Composable
@Preview(showBackground = true)
fun UserItemPreview() {
    GithubUserComposeTheme {
        UserItem(
            R.drawable.logo,
            "Jaket Hoodie Dicoding",
            4000)
    }
}