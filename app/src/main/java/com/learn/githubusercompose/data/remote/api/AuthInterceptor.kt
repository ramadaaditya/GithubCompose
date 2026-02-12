package com.learn.githubusercompose.data.remote.api

import com.learn.githubusercompose.data.settings.GithubTokenProvider
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenProvider: GithubTokenProvider
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenProvider.getTokenBlocking()
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .addHeader("Accept", "application/json")

        token?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(requestBuilder.build())
    }

}