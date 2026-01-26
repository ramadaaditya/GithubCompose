package com.learn.githubusercompose.core.utils

import androidx.compose.ui.graphics.Color

fun languageColor(language: String): Color = when (language.lowercase()) {
    "kotlin" -> Color(0xFFA97BFF)
    "java" -> Color(0xFFB07219)
    "javascript" -> Color(0xFFF1E05A)
    else -> Color.White
}