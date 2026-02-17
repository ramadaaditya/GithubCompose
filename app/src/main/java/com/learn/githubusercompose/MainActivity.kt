package com.learn.githubusercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.learn.githubusercompose.connection.ConnectivityViewModel
import com.learn.githubusercompose.ui.GithubApp
import com.learn.githubusercompose.ui.rememberGithubAppState
import com.learn.githubusercompose.ui.theme.GithubUserComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: ConnectivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubUserComposeTheme {
                val appState = rememberGithubAppState(
                    viewModel.isConnected
                )
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GithubApp(appState)
                }
            }
        }
    }
}
