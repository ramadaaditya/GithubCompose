package com.learn.githubusercompose.di

import com.learn.githubusercompose.data.UserRepository

object Injection {
    fun provideRepository(): UserRepository {
        return UserRepository.getInstance()
    }
}