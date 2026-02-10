package com.learn.githubusercompose.connection

import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface ConnectivityObserver {
    val isConnected: Flow<Boolean>
}