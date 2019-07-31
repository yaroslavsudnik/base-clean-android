package dev.sudnik.domain.interactor

import dev.sudnik.basecleanandroid.domain.BaseInteractor
import dev.sudnik.basecleanandroid.domain.ErrorResponse
import dev.sudnik.domain.entity.PostEntity
import dev.sudnik.domain.repository.Repository
import dev.sudnik.domain.state.PostDataState

class PostInteractor(private val repository: Repository) :
        BaseInteractor<PostDataState, PostEntity>() {

    fun getPostList() = call(PostDataState.PostLoaded.create()) { repository.getPostList(it) }

    override fun processSuccessState(clazz: PostDataState, data: PostEntity):
            PostDataState = when (clazz) {
        is PostDataState.PostLoaded -> PostDataState.PostLoaded(data.post)
        else -> throw IllegalArgumentException(
                "State object $clazz has not been determined"
        )
    }

    override fun processErrorState(error: ErrorResponse): PostDataState = PostDataState.PostError
}