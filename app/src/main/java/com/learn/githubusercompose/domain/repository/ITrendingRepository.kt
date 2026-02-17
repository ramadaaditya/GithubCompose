package com.learn.githubusercompose.domain.repository

import com.learn.githubusercompose.domain.model.Result
import com.learn.githubusercompose.domain.model.TrendingRepo
import kotlinx.coroutines.flow.Flow

interface ITrendingRepository {
    fun getTrendingRepositories(query: String): Flow<Result<List<TrendingRepo>>>
}