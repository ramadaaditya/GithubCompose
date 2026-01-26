package com.learn.githubusercompose.data.remote.api

import com.learn.githubusercompose.data.remote.response.DetailUserResponse
import com.learn.githubusercompose.data.remote.response.FollowResponse
import com.learn.githubusercompose.data.remote.response.SearchRepositoriesResponse
import com.learn.githubusercompose.data.remote.response.SearchUserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    @GET("search/users")
    suspend fun searchUser(
        @Query("q") query: String
    ): SearchUserResponse

    @GET("search/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String
    ): DetailUserResponse

    @GET("search/repositories")
    suspend fun searchRepository(
        @Query("q") query: String,
        @Query("sort") sort: String?,
        @Query("order") order: String?,
        @Query("per_page") perPage: Int,
    ): SearchRepositoriesResponse

    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username: String
    ): FollowResponse

    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username") username: String
    ): FollowResponse
}