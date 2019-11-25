package dev.sudnik.presentation

import android.app.Application
import dev.sudnik.basecleanandroid.presentation.BaseReducer
import dev.sudnik.basecleanandroid.presentation.BaseViewModel

class MainViewModel(application: Application) : BaseViewModel<MainViewState>(application) {

    private lateinit var userReducer: UserReducer
    private lateinit var postReducer: PostReducer

    override fun instanceReducers(): ArrayList<BaseReducer<MainViewState, out Any>> {
        userReducer = UserReducer()
        postReducer = PostReducer()
        return arrayListOf(userReducer, postReducer)
    }

    fun getUserName() = userReducer.getUserName("groupId")

    fun getAuthorName() = postReducer.getAuthorName("postId")
}
