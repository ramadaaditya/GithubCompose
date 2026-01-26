package com.learn.githubusercompose.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.learn.githubusercompose.data.local.database.UserDatabase
import com.learn.githubusercompose.data.local.entity.SearchUserEntity
import com.learn.githubusercompose.data.remote.api.ApiServices

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator(
    private val database: UserDatabase,
    private val apiService: ApiServices
) : RemoteMediator<Int, SearchUserEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SearchUserEntity>
    ): MediatorResult {
        TODO("Not yet implemented")
    }

}