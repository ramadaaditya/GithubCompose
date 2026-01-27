package com.learn.githubusercompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.learn.githubusercompose.R
import com.learn.githubusercompose.domain.model.DetailUser
import com.learn.githubusercompose.ui.theme.GithubUserComposeTheme

@Composable
fun UserItem(
    user: DetailUser,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = 180.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor =
                MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = user.avatarUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    error = painterResource(R.drawable.logo), // Ganti dengan resource placeholder Anda
                    modifier = Modifier
                        .size(56.dp) // Ukuran sedikit lebih besar
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Name & Handle
                Column(
                    modifier = Modifier.weight(1f) // Mengambil sisa ruang agar tombol terdorong ke kanan
                ) {
                    Text(
                        text = "David Chen", // Sebaiknya ini field user.name
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    Text(
                        text = "@${user.username} • Remote",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }

                // Button Follow
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1F6FEB)), // Biru terang
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
                    modifier = Modifier.height(32.dp) // Tombol lebih pendek/ramping
                ) {
                    Text(
                        text = "Follow",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- BIO SECTION ---
            Text(
                text = user.bio,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 20.sp
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Divider halus (Opsional, di gambar sebenarnya jarang pakai garis tegas)
            // HorizontalDivider(color = Color.DarkGray, thickness = 0.5.dp)

            // --- FOOTER / STATS SECTION ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp) // Jarak antar item statistik
            ) {
                // Helper Composable untuk Item Statistik agar kode bersih
                StatItem(
                    icon = Icons.Outlined.Description,
                    text = "${user.repoCount} Repos"
                )

                StatItem(
                    icon = Icons.Default.People,
                    text = "${user.follower} Followers"
                )

                // Language Item (Custom dengan Dot)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(
                                MaterialTheme.colorScheme.primaryContainer
                            )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "TypeScript",
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface)
                    )
                }
            }
        }
    }
}

// Extracted Component untuk item statistik (Reusability)
@Composable
fun StatItem(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserItemPreviewRefactored() {
    GithubUserComposeTheme(dynamicColor = false) {
        UserItem(
            user = DetailUser(
                id = 1,
                username = "dchen_code",
                repoCount = 120,
                follower = 850,
                following = 45,
                avatarUrl = "",
                bio = "React Specialist building accessible UI libraries. Creating tools for the modern web. Mobile security specialist",
                name = "Ramada",
                email = "mramadaaditya@gmail.com",
                location = "Banyumas"
            )
        )
    }
}