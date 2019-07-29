package dev.sudnik.presentation

import android.app.Application
import dev.sudnik.basecleanandroid.presentation.BaseReducer
import dev.sudnik.basecleanandroid.presentation.BaseViewModel

class MainViewModel(application: Application) : BaseViewModel<MainViewState>(application) {

    private lateinit var userReducer: UserReducer
    private lateinit var postReducer: PostReducer

    override fun unknownError(): MainViewState =
            MainViewState.OnError("unknown error")

    override val reducers: ArrayList<BaseReducer<MainViewState, out Any?>>
        get() {
            userReducer = UserReducer()
            postReducer = PostReducer()
            return arrayListOf(userReducer, postReducer)
        }

    fun getUserName() {
        userReducer.getUserName()
    }

    fun getAuthorName() {
        postReducer.getAuthorName()
    }
}