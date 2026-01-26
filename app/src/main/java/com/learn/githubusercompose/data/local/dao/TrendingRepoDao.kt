package com.learn.githubusercompose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.learn.githubusercompose.data.local.entity.TrendingRepoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrendingRepoDao {
    @Query("SELECT * FROM trending_repo")
    fun getAllTrendingRepo(): Flow<List<TrendingRepoEntity>>

    @Insert(onConflict = REPLACE)
    fun insertAllTrendingRepo(trendingRepo: List<TrendingRepoEntity>)

    @Query("DELETE FROM trending_repo")
    fun deleteAll()

    @Transaction
    suspend fun updateTrendingCache(repos: List<TrendingRepoEntity>) {
        deleteAll()
        insertAllTrendingRepo(repos)
    }
}