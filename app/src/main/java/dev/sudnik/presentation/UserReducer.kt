package dev.sudnik.presentation

import dev.sudnik.basecleanandroid.domain.DataState
import dev.sudnik.basecleanandroid.presentation.BaseReducer
import dev.sudnik.basecleanandroid.presentation.State.DefaultError
import dev.sudnik.basecleanandroid.presentation.State.DefaultError.ServerToNativeError
import dev.sudnik.data.RepositoryImpl
import dev.sudnik.domain.interactor.LoadUsersInteractor
import dev.sudnik.domain.state.UserDataState

class UserReducer : BaseReducer<MainViewState, UserDataState>() {
    private val usersInteractor = LoadUsersInteractor(RepositoryImpl())

    override fun processDataState(data: UserDataState): MainViewState = when (data) {
        is UserDataState.UserListLoaded -> MainViewState.ShowName(data.users[0].name)
        else -> super.processDataState(data)
    }

    override fun processErrorState(error: UserDataState): MainViewState = when (error) {
        is UserDataState.OnUserListEmptyError -> MainViewState.OnError("User list is empty")
        else -> super.processErrorState(error)
    }

    override fun processServerErrorState(data: DataState.ServerError<UserDataState>): DefaultError<MainViewState> =
        when (data) {
            is DataState.ServerError.NotFound -> ServerToNativeError(MainViewState.OnError("custom auth nativeError"))
            else -> super.processServerErrorState(data)
        }

    override fun unknownError(): MainViewState = MainViewState.OnError("user name loading error")

    fun getUserName() {
        request { usersInteractor.getUserList(it) }
    }

    fun getUserSurname() {
        request { usersInteractor.getUserSurname(it) }
    }
}