package com.learn.githubusercompose.data.remote

import com.learn.githubusercompose.data.remote.api.ApiServices
import com.learn.githubusercompose.data.remote.response.DetailUserResponse
import com.learn.githubusercompose.data.remote.response.FollowResponseItem
import com.learn.githubusercompose.data.remote.response.SearchUserResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val apiService: ApiServices
) {

    suspend fun searchUsers(query: String): SearchUserResponse = apiService.searchUser(query)


    suspend fun getDetailUser(username: String): DetailUserResponse =
        apiService.getDetailUser(username)

    suspend fun getFollowing(username: String): List<FollowResponseItem> =
        apiService.getFollowing(username)


    suspend fun getFollowers(username: String): List<FollowResponseItem> =
        apiService.getFollowers(username)
}