package com.learn.githubusercompose.domain.repository

import com.learn.githubusercompose.data.Resource
import com.learn.githubusercompose.domain.model.TrendingRepo
import kotlinx.coroutines.flow.Flow

interface ITrendingRepository {
    fun getTrendingRepositories(query: String): Flow<Resource<List<TrendingRepo>>>
}