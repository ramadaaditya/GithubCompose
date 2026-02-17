package com.learn.githubusercompose.data.repository

import com.learn.githubusercompose.core.common.networkBoundResource
import com.learn.githubusercompose.domain.model.Result
import com.learn.githubusercompose.data.local.dao.TrendingRepoDao
import com.learn.githubusercompose.data.remote.api.ApiServices
import com.learn.githubusercompose.data.remote.dto.toDomain
import com.learn.githubusercompose.data.remote.dto.toEntity
import com.learn.githubusercompose.domain.model.TrendingRepo
import com.learn.githubusercompose.domain.repository.ITrendingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrendingRepository @Inject constructor(
    private val apiService: ApiServices,
    private val dao: TrendingRepoDao
) : ITrendingRepository {

    companion object {
        private const val ITEM_LIMIT = 10
    }

    override fun getTrendingRepositories(query: String): Flow<Result<List<TrendingRepo>>> {
        return networkBoundResource(
            query = {
                dao.getAllTrendingRepo().map { entities ->
                    entities.map { it.toDomain() }
                }
            },
            fetch = {
                apiService.searchRepository(
                    query,
                    sort = "stars",
                    order = "desc",
                    perPage = ITEM_LIMIT
                )
            },
            saveFetchResult = { response ->
                val entities = response.items?.map { item ->
                    item.toEntity()
                } ?: emptyList()
                dao.updateTrendingCache(entities)
            },
            shouldFetch = { data ->
                data.isEmpty()
            }
        )
    }
}


