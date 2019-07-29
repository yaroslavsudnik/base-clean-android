package dev.sudnik.domain.repository

import dev.sudnik.basecleanandroid.domain.OnCallback
import dev.sudnik.domain.entity.PostEntity
import dev.sudnik.domain.entity.UserEntity

interface Repository {
    fun getUserList(callback: OnCallback<UserEntity>)
    fun getPostList(callback: OnCallback<PostEntity>)
}
