package com.learn.githubusercompose.data.repository

import com.learn.githubusercompose.core.common.networkBoundResource
import com.learn.githubusercompose.data.remote.RemoteDataSource
import com.learn.githubusercompose.data.remote.dto.toDomain
import com.learn.githubusercompose.data.remote.dto.toDomainUser
import com.learn.githubusercompose.data.remote.dto.toEntity
import com.learn.githubusercompose.data.remote.dto.toFavoriteEntity
import com.learn.githubusercompose.data.remote.dto.toFollowerEntity
import com.learn.githubusercompose.data.remote.dto.toFollowingEntity
import com.learn.githubusercompose.data.remote.dto.toUserDomains
import com.learn.githubusercompose.data.remote.dto.toUserEntity
import com.learn.githubusercompose.domain.model.DetailUser
import com.learn.githubusercompose.domain.model.Result
import com.learn.githubusercompose.domain.model.User
import com.learn.githubusercompose.domain.repository.IUserRepository
import com.learn.githubusercompose.domain.source.LocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : IUserRepository {
    override fun searchUsers(query: String): Flow<Result<List<User>>> {
        return networkBoundResource(
            query = {
                localDataSource.getAllUsers().map { entities ->
                    entities.map { it.toDomainUser() }
                }
            },
            fetch = {
                remoteDataSource.searchUsers(query)
            },
            saveFetchResult = { response ->
                val entities = response.items.map { it.toUserEntity() }
                localDataSource.replaceUsers(entities)
            }
        )
    }

    override fun getDetailUser(username: String): Flow<Result<DetailUser?>> {
        return networkBoundResource(
            query = {
                localDataSource.getDetailUser(username).map { entity ->
                    entity?.toDomain()
                }
            },
            fetch = {
                remoteDataSource.getDetailUser(username)
            },
            saveFetchResult = { response ->
                val entity = response.toEntity()

                localDataSource.insertDetailUser(entity)
            },
            shouldFetch = { data ->
                data == null
            }
        )
    }

    override fun getFollowing(username: String): Flow<Result<List<User>>> {
        return networkBoundResource(
            query = {
                localDataSource.getFollowing(username).map { entities ->
                    entities.map { it.toDomain() }
                }
            },
            fetch = {
                remoteDataSource.getFollowing(username)
            },
            saveFetchResult = { response ->
                val entity = response.map {
                    it.toFollowingEntity(owner = username)
                }
                localDataSource.updateFollowing(username, entity)
            },
            shouldFetch = { data ->
                data.isEmpty()
            }
        )
    }

    override fun getFollowers(username: String): Flow<Result<List<User>>> {
        return networkBoundResource(
            query = {
                localDataSource.getFollowers(username).map { entities ->
                    entities.map { it.toDomain() }
                }
            },
            fetch = {
                remoteDataSource.getFollowers(username)
            },
            saveFetchResult = { response ->
                val entity = response.map {
                    it.toFollowerEntity(owner = username)
                }
                localDataSource.updateFollowers(username, entity)
            },
            shouldFetch = { data ->
                data.isEmpty()
            }
        )
    }

    override fun getAllFavorites(): Flow<Result<List<User>>> {
        return localDataSource.getAllFavorites()
            .map { entities ->
                val users = entities.map {
                    it.toDomain()
                }
                Result.Success(users) as Result<List<User>>
            }
    }

    override suspend fun insertFavorite(user: User) {
        localDataSource.insertFavorite(user.toFavoriteEntity())
    }

    override suspend fun deleteFavorite(id: Int) {
        localDataSource.deleteFavoriteById(id)
    }

    override fun getUsersStream(): Flow<List<User>> {
        return localDataSource.getAllUsers().map { entities ->
            entities.toUserDomains()
        }
    }
}

