package com.learn.githubusercompose.di

import com.learn.githubusercompose.data.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/*
Using abstract for owned class
 */

//@Module
//@InstallIn(SingletonComponent::class)
//abstract class AppModule {
//    @Provides
//    @Singleton
//    abstract fun provideUserRepository(): UserRepository
//}