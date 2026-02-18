package com.learn.githubusercompose.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices.PIXEL_5
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun GithubTokenScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val savedToken by viewModel.githubToken.collectAsStateWithLifecycle()
    var token by rememberSaveable { mutableStateOf("") }
    var isTokenVisible by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(
        savedToken
    ) {
        savedToken?.let {
            if (token.isEmpty()) {
                token = it
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "GitHub Token",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Masukkan Personal Access Token GitHub kamu. Token ini akan digunakan untuk autentikasi API.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = token,
            onValueChange = { token = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Personal Access Token") },
            placeholder = { Text("ghp_xxxxxxxxxxxxxx") },
            singleLine = true,
            visualTransformation = if (isTokenVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(
                    onClick = { isTokenVisible = !isTokenVisible }
                ) {
                    Icon(
                        imageVector = if (isTokenVisible)
                            Icons.Outlined.VisibilityOff
                        else
                            Icons.Outlined.Visibility,
                        contentDescription = null
                    )
                }
            },
            supportingText = {
                Text("Token disimpan secara lokal di perangkat")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))



        Button(
            onClick = { viewModel.saveGithubToken(token) },
            enabled = token.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Token")
        }

//        // Tombol Clear Token (opsional)
//        if (!savedToken.isNullOrBlank()) {
//            OutlinedButton(
//                onClick = {
//                    viewModel.clearGithubToken()
//                    token = ""
//                    scope.launch {
//                        snackbarHostState.showSnackbar("Token berhasil dihapus")
//                    }
//                },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Clear Token")
//            }
//        }
    }
}


@Preview(showBackground = true, device = PIXEL_5)
@Composable
private fun GithubTokenScreenPreview() {
    GithubTokenScreen(
        onBackClick = {}
    )
}