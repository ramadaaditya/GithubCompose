package com.learn.githubusercompose.data.local

import androidx.room.withTransaction
import com.learn.githubusercompose.data.local.dao.DetailUserDao
import com.learn.githubusercompose.data.local.dao.FavoriteUserDao
import com.learn.githubusercompose.data.local.dao.FollowDao
import com.learn.githubusercompose.data.local.dao.UserDao
import com.learn.githubusercompose.data.local.database.UserDatabase
import com.learn.githubusercompose.data.local.entity.DetailUserEntity
import com.learn.githubusercompose.data.local.entity.FavoriteUserEntity
import com.learn.githubusercompose.data.local.entity.FollowerEntity
import com.learn.githubusercompose.data.local.entity.FollowingEntity
import com.learn.githubusercompose.data.local.entity.UserEntity
import com.learn.githubusercompose.domain.source.LocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val database: UserDatabase,
    private val userDao: UserDao,
    private val favoriteDao: FavoriteUserDao,
    private val detailUserDao: DetailUserDao,
    private val followDao: FollowDao,
) : LocalDataSource {

    override suspend fun insertDetailUser(user: DetailUserEntity) {
        return detailUserDao.insertDetailUser(user)
    }

    override suspend fun insertUsers(users: List<UserEntity>) {
        return userDao.insertUsers(users)
    }

    override fun getDetailUser(username: String): Flow<DetailUserEntity?> {
        return detailUserDao.getDetailUser(username)
    }

    override fun getFollowing(username: String): Flow<List<FollowingEntity>> {
        return followDao.getFollowing(username)
    }

    override suspend fun updateFollowing(
        username: String,
        list: List<FollowingEntity>
    ) {
        database.withTransaction {
            followDao.deleteFollowingByUsername(username)
            followDao.insertFollowing(list)
        }

    }

    override fun getFollowers(username: String): Flow<List<FollowerEntity>> {
        return followDao.getFollowers(username)
    }

    override suspend fun updateFollowers(
        username: String,
        list: List<FollowerEntity>
    ) {
        database.withTransaction {
            followDao.deleteFollowersByUsername(username)
            followDao.insertFollowers(list)
        }
    }

    override fun getAllFavorites(): Flow<List<FavoriteUserEntity>> {
        return favoriteDao.getAllFavorites()
    }

    override suspend fun insertFavorite(user: FavoriteUserEntity) {
        favoriteDao.insertFavorite(user)
        userDao.updateFavoriteState(user.id, true)
    }

    override suspend fun deleteFavoriteById(id: Int) {
        database.withTransaction {
            val user = userDao.getUserById(id)
            user?.let {
                favoriteDao.deleteFavoriteByUsername(it.username)
                userDao.updateFavoriteState(id, false)
            }
        }
    }

    override fun getAllUsers(): Flow<List<UserEntity>> {
        return userDao.getAllUser()
    }

    override suspend fun replaceUsers(users: List<UserEntity>) {
        database.withTransaction {
            userDao.deleteAllNonFavorites()
            userDao.insertUsers(users)
        }
    }

    override suspend fun getUserById(id: Int): UserEntity? {
        return userDao.getUserById(id)
    }
}

