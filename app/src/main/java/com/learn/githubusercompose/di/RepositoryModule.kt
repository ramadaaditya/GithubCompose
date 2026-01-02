package com.learn.githubusercompose.di

import com.learn.githubusercompose.data.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//abstract class RepositoryModule {
//    @Binds
//    @Singleton
//    abstract fun bindUserRepository(
//        userRepository : UserRepository
//    )
//}