package com.learn.githubusercompose.data.remote.dto

import com.learn.githubusercompose.data.local.entity.UserEntity
import com.learn.githubusercompose.data.remote.response.ItemsItem

fun ItemsItem.toUserEntity(): UserEntity {
    return UserEntity(
        username = this.login,
        avatarUrl = this.avatarUrl,
        htmlUrl = this.htmlUrl
    )
}

fun List<ItemsItem>.toUserEntities(): List<UserEntity> {
    return this.map { it.toUserEntity() }
}