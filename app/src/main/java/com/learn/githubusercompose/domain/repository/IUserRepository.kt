package com.learn.githubusercompose.domain.repository

import com.learn.githubusercompose.data.Resource
import com.learn.githubusercompose.domain.model.DetailUser
import com.learn.githubusercompose.domain.model.UserItemUiState
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun searchUsers(query: String): Flow<Resource<List<UserItemUiState>>>
    fun getDetailUser(username: String): Flow<Resource<DetailUser>>
    fun getFollowing(username: String): Flow<Resource<List<UserItemUiState>>>
    fun getFollowers(username: String): Flow<Resource<List<UserItemUiState>>>
}