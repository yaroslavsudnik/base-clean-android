package dev.sudnik.presentation

import dev.sudnik.basecleanandroid.presentation.BaseReducer
import dev.sudnik.data.RepositoryImpl
import dev.sudnik.domain.interactor.PostInteractor
import dev.sudnik.domain.state.PostDataState

class PostReducer : BaseReducer<MainViewState, PostDataState>() {

    override fun instanceInteractor() = PostInteractor(RepositoryImpl())

    override fun processDataState(data: PostDataState): MainViewState = when (data) {
        is PostDataState.PostLoaded -> MainViewState.ShowName(data.post.authorName)
        else -> super.processDataState(data)
    }

    override fun processErrorState(error: PostDataState): MainViewState = when (error) {
        is PostDataState.PostError -> MainViewState.OnError("post error")
        else -> super.processErrorState(error)
    }

    override fun unknownError(): MainViewState = MainViewState.OnError("author name loading error")

    fun getAuthorName(postId: String) = (interactor as PostInteractor).getPost(postId)
}
