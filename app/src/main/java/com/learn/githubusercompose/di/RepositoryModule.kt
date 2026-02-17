package com.learn.githubusercompose.di

import com.learn.githubusercompose.data.local.LocalDataSourceImpl
import com.learn.githubusercompose.data.repository.TrendingRepository
import com.learn.githubusercompose.data.repository.UserRepository
import com.learn.githubusercompose.data.settings.SettingsRepositoryImpl
import com.learn.githubusercompose.domain.repository.ITrendingRepository
import com.learn.githubusercompose.domain.repository.IUserRepository
import com.learn.githubusercompose.domain.repository.SettingsRepository
import com.learn.githubusercompose.domain.source.LocalDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepository: UserRepository
    ): IUserRepository

    @Binds
    @Singleton
    abstract fun bindTrendingRepository(
        trendingRepository: TrendingRepository
    ): ITrendingRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(
        implSettingsRepository: SettingsRepositoryImpl
    ): SettingsRepository


    @Binds
    @Singleton
    abstract fun provideLocalDataSource(
        impl: LocalDataSourceImpl
    ): LocalDataSource

}