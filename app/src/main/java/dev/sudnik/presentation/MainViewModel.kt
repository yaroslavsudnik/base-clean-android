package dev.sudnik.presentation

import android.app.Application
import dev.sudnik.basecleanandroid.presentation.BaseReducer
import dev.sudnik.basecleanandroid.presentation.BaseViewModel
import dev.sudnik.data.RepositoryImpl
import dev.sudnik.domain.interactor.LoadUsersInteractor
import dev.sudnik.domain.interactor.PostInteractor

class MainViewModel(application: Application) : BaseViewModel<MainViewState>(application) {

    private lateinit var userReducer: UserReducer
    private lateinit var postReducer: PostReducer

    override fun instanceReducers(): ArrayList<BaseReducer<MainViewState, out Any>> {
        userReducer = UserReducer(LoadUsersInteractor(RepositoryImpl()))
        postReducer = PostReducer(PostInteractor(RepositoryImpl()))
        return arrayListOf(userReducer, postReducer)
    }

    override fun unknownError(): MainViewState = MainViewState.OnError("unknown error")

    fun getUserName() = userReducer.getUserName()

    fun getAuthorName() = postReducer.getAuthorName()
}