package com.learn.githubusercompose.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.learn.githubusercompose.R
import com.learn.githubusercompose.domain.model.User
import com.learn.githubusercompose.ui.GithubIcons
import com.learn.githubusercompose.ui.theme.GithubUserComposeTheme

@Composable
fun SearchUserItem(
    state: User,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    displayFavorite: Boolean = false,
    onFavoriteClick: (User) -> Unit = {}
) {
    val isFavorite = state.isFavorite
    Card(
        onClick = { onItemClick(state.username) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            AsyncImage(
                model = state.avatarUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.logo),
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = state.username,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Text(
                    text = "ID: ${state.id}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
            if (displayFavorite) {
                IconButton(
                    modifier = Modifier
                        .padding(end = 12.dp),
                    onClick = { onFavoriteClick(state) }
                ) {
                    Icon(
                        imageVector = if (isFavorite) GithubIcons.selectedFavorite else GithubIcons.unselectedFavorite,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "View Detail",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchUserItemPreview() {
    GithubUserComposeTheme() {
        SearchUserItem(
            state = User(
                id = 412312,
                username = "Ramados",
                avatarUrl = "Ini Error Avatar",
            ),
            onItemClick = {},
            displayFavorite = true
        )
    }

}


@Preview(showBackground = true)
@Composable
private fun SearchUserItemPreview_nofavorite() {
    GithubUserComposeTheme() {
        SearchUserItem(
            state = User(
                id = 412312,
                username = "Ramados",
                avatarUrl = "Ini Error Avatar",
            ),
            onItemClick = {},
            displayFavorite = false
        )
    }
}