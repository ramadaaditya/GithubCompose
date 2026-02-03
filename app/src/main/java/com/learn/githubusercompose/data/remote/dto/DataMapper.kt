package com.learn.githubusercompose.data.remote.dto

import com.learn.githubusercompose.data.local.entity.DetailUserEntity
import com.learn.githubusercompose.data.local.entity.FollowerEntity
import com.learn.githubusercompose.data.local.entity.FollowingEntity
import com.learn.githubusercompose.data.local.entity.SearchUserEntity
import com.learn.githubusercompose.data.local.entity.TrendingRepoEntity
import com.learn.githubusercompose.data.remote.response.DetailUserResponse
import com.learn.githubusercompose.data.remote.response.FollowResponseItem
import com.learn.githubusercompose.data.remote.response.ItemsItem
import com.learn.githubusercompose.data.remote.response.RepositoryItem
import com.learn.githubusercompose.domain.model.DetailUser
import com.learn.githubusercompose.domain.model.TrendingRepo
import com.learn.githubusercompose.domain.model.UserItemUiState

fun ItemsItem?.toUserEntity(): SearchUserEntity {
    return SearchUserEntity(
        id = this?.id ?: 0,
        username = this?.login ?: "Unknown User",
        avatarUrl = this?.avatarUrl ?: "",
    )
}

fun SearchUserEntity.toDomainUser(): UserItemUiState {
    return UserItemUiState(
        id = this.id,
        username = this.username,
        avatarUrl = this.avatarUrl ?: "",
        isFavorite = this.isFavorite
    )
}

fun FollowingEntity.toDomain(): UserItemUiState {
    return UserItemUiState(
        id = this.id,
        username = this.username,
        avatarUrl = this.avatarUrl,
        isFavorite = this.isFavorite
    )
}

fun FollowerEntity.toDomain(): UserItemUiState {
    return UserItemUiState(
        id = this.id,
        username = this.username,
        avatarUrl = this.avatarUrl,
        isFavorite = this.isFavorite
    )
}

fun DetailUserEntity?.toDomain(): DetailUser {
    return DetailUser(
        id = this?.id ?: 0,
        following = this?.following ?: 0,
        followers = this?.followers ?: 0,
        name = this?.name ?: "Unknown",
        username = this?.username ?: "Unknown",
        repoCount = this?.repoCount ?: 0,
        bio = this?.bio ?: "Bio is not set",
        email = this?.email ?: "Email not set",
        location = this?.location ?: "Location unknown",
        avatarUrl = this?.avatarUrl ?: "Unknown",
    )
}

fun DetailUserResponse.toEntity(): DetailUserEntity {
    return DetailUserEntity(
        id = this.id ?: 0,
        following = this.following ?: 0,
        followers = this.followers ?: 0,
        name = this.name ?: "Unknown",
        username = this.login ?: "Unknown",
        repoCount = this.publicRepos ?: 0,
        bio = this.bio ?: "Bio is not set",
        email = this.email ?: "Email not set",
        location = this.location ?: "Location unknown",
        avatarUrl = this.avatarUrl ?: "Unknown",
    )
}

fun RepositoryItem.toEntity(): TrendingRepoEntity {
    return TrendingRepoEntity(
        id = this.id ?: 0,
        name = this.name ?: "Unnamed Repository",
        fullName = this.fullName ?: this.name ?: "",
        description = this.description ?: "No description provided",
        ownerName = this.owner?.login ?: "Unknown Owner",
        ownerAvatarUrl = this.owner?.avatarUrl ?: "",
        stars = this.stargazersCount ?: 0,
        language = this.language ?: "N/A",
        forkCount = this.forksCount ?: 0
    )
}

fun TrendingRepoEntity.toDomain(): TrendingRepo {
    return TrendingRepo(
        id = this.id,
        name = this.name,
        fullName = this.fullName,
        description = this.description,
        ownerName = this.ownerName,
        ownerAvatarUrl = this.ownerAvatarUrl,
        stars = this.stars,
        language = this.language,
        forkCount = this.forkCount
    )
}

fun FollowResponseItem.toDomain(): UserItemUiState {
    return UserItemUiState(
        id = this.id ?: 0,
        username = this.login ?: "Unknown",
        avatarUrl = this.avatarUrl ?: "Avatar Not Found",
    )
}

fun FollowResponseItem.toFollowingEntity(owner: String): FollowingEntity {
    return FollowingEntity(
        id = this.id ?: 0,
        username = this.login ?: "Unknown",
        avatarUrl = this.avatarUrl ?: "Avatar Not Found",
        ownerName = owner
    )
}

fun FollowResponseItem.toFollowerEntity(owner: String): FollowerEntity {
    return FollowerEntity(
        id = this.id ?: 0,
        username = this.login ?: "Unknown",
        avatarUrl = this.avatarUrl ?: "Avatar Not Found",
        ownerName = owner
    )
}

fun List<ItemsItem>?.toUserEntities(): List<SearchUserEntity> {
    return this?.map { it.toUserEntity() } ?: emptyList()
}

fun List<FollowResponseItem>?.toFollowDomain(): List<UserItemUiState> {
    return this?.map { it.toDomain() } ?: emptyList()
}

fun List<SearchUserEntity>?.toUserDomains(): List<UserItemUiState> {
    return this?.map { it.toDomainUser() } ?: emptyList()
}

fun List<RepositoryItem>?.toDomainRepositories(): List<TrendingRepo> {
    return this?.map { it.toDomainTrendingRepository() } ?: emptyList()
}

fun RepositoryItem.toDomainTrendingRepository(): TrendingRepo {
    return TrendingRepo(
        id = this.id ?: 0,
        name = this.name ?: "Unnamed Repository",
        fullName = this.fullName ?: this.name ?: "",
        description = this.description ?: "No description provided",
        ownerName = this.owner?.login ?: "Unknown Owner",
        ownerAvatarUrl = this.owner?.avatarUrl ?: "",
        stars = this.stargazersCount ?: 0,
        language = this.language ?: "N/A",
        forkCount = this.forksCount ?: 0
    )
}

fun DetailUserResponse.toDetailUserDomain(): DetailUser {
    return DetailUser(
        following = this.following ?: 0,
        followers = this.followers ?: 0,
        name = this.name ?: "Unknown",
        username = this.login ?: "Unknown",
        repoCount = this.publicRepos ?: 0,
        bio = this.bio ?: "Bio is not set",
        email = this.email ?: "Email not set",
        location = this.location ?: "Location unknown",
        avatarUrl = this.avatarUrl ?: "Unknown",
        id = this.id ?: 0
    )
}