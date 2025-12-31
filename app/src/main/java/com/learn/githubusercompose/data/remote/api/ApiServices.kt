package com.learn.githubusercompose.data.remote.api

import com.learn.githubusercompose.data.remote.response.SearchUserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("search/users")
    suspend fun searchUser(
        @Query("q") query: String
    ): SearchUserResponse
}