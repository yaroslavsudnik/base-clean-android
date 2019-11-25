package dev.sudnik.domain.repository

import dev.sudnik.basecleanandroid.domain.RepositoryResponse
import dev.sudnik.domain.entity.PostEntity
import dev.sudnik.domain.entity.UserEntity

interface Repository {
    fun getUserList(groupId: String, response: RepositoryResponse<UserEntity>)
    fun getPost(postId: String, response: RepositoryResponse<PostEntity>)
}
