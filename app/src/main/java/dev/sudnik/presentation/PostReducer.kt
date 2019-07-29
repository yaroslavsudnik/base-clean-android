package dev.sudnik.presentation

import dev.sudnik.basecleanandroid.domain.DataState
import dev.sudnik.basecleanandroid.presentation.BaseReducer
import dev.sudnik.basecleanandroid.presentation.State.DefaultError
import dev.sudnik.data.RepositoryImpl
import dev.sudnik.domain.interactor.PostInteractor
import dev.sudnik.domain.state.PostDataState

class PostReducer : BaseReducer<MainViewState, PostDataState>() {
    private val postsInteractor = PostInteractor(RepositoryImpl())

    override fun processDataState(data: PostDataState): MainViewState = when (data) {
        is PostDataState.PostLoaded -> MainViewState.ShowName(data.post.authorName)
        else -> super.processDataState(data)
    }

    override fun processErrorState(error: PostDataState): MainViewState = when (error) {
        is PostDataState.PostError -> MainViewState.OnError("post error")
        else -> super.processErrorState(error)
    }

    override fun processServerErrorState(data: DataState.ServerError<PostDataState>): DefaultError<MainViewState> =
        when (data) {
            is DataState.ServerError.NotFound ->
                DefaultError.ServerToNativeError(MainViewState.OnError("custom nativeError"))
            else -> super.processServerErrorState(data)
        }

    override fun unknownError(): MainViewState = MainViewState.OnError("author name loading error")

    fun getAuthorName() {
        request { postsInteractor.getPostList(it) }
    }
}