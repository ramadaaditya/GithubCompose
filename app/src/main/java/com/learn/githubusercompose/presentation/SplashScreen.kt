package com.learn.githubusercompose.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.learn.githubusercompose.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onAnimationFinished: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onBackground),
        contentAlignment = Alignment.Center
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.github))
        val logoAnimationState = animateLottieCompositionAsState(
            composition = composition,
            isPlaying = true
        )

        // 1. ANIMASI
        LottieAnimation(
            composition = composition,
            progress = { logoAnimationState.progress },
            Modifier.size(250.dp)
        )

        // 2. NAVIGASI OTOMATIS
        LaunchedEffect(key1 = logoAnimationState.isAtEnd) {
            if (logoAnimationState.isAtEnd) {
                delay(1000)
                onAnimationFinished()
            }
        }

        // 3. ATRIBUSI (Diubah dari HTML ke AnnotatedString)
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Kita membangun String yang kaya format
            val attributionText = buildAnnotatedString {
                // Style untuk Link (Underline + Sedikit Bold)
                val linkStyle = SpanStyle(
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f)
                )

                // Style untuk Teks Biasa ("by", "on")
                val normalStyle = SpanStyle(
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                    fontWeight = FontWeight.Light
                )

                // "Github"
                withStyle(style = linkStyle) {
                    append("Github")
                }

                // " by "
                withStyle(style = normalStyle) {
                    append(" by ")
                }

                // "Thao Phan"
                withStyle(style = linkStyle) {
                    append("Thao Phan")
                }

                // " on "
                withStyle(style = normalStyle) {
                    append(" on ")
                }

                // "IconScout"
                withStyle(style = linkStyle) {
                    append("IconScout")
                }
            }

            Text(
                text = "v1.0.0",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Render text yang sudah di-style tadi
            Text(
                text = attributionText,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                textAlign = TextAlign.Center
            )
        }
    }
}