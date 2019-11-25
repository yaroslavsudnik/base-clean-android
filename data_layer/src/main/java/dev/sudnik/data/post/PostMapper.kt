package dev.sudnik.data.post

import dev.sudnik.domain.model.Post

class PostMapper {
    fun transformToPost(dto: PostDTO) = Post(dto.authorName, dto.id, dto.title)
}
