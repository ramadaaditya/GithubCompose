package com.learn.githubusercompose.data

import com.learn.githubusercompose.model.FakeUserDataSource
import com.learn.githubusercompose.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor() {
    private val users = mutableListOf<User>()

    init {
        if (users.isEmpty()) {
            FakeUserDataSource.dummyUser.forEach {
                users.add(it)
            }
        }
    }

    fun getUsers(): Flow<List<User>> = flowOf(users)

    fun getUserById(userId: Long): User = users.first { it.id == userId }

    fun searchUsers(query: String): List<User> = users.filter {
        it.login.contains(query, ignoreCase = true)
    }
}