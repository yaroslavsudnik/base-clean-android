package dev.sudnik.presentation

import dev.sudnik.basecleanandroid.presentation.BaseReducer
import dev.sudnik.data.RepositoryImpl
import dev.sudnik.domain.interactor.LoadUsersInteractor
import dev.sudnik.domain.state.UserDataState

class UserReducer : BaseReducer<MainViewState, UserDataState>() {

    override fun instanceInteractor() = LoadUsersInteractor(RepositoryImpl())

    override fun processDataState(data: UserDataState): MainViewState = when (data) {
        is UserDataState.UserListLoaded -> MainViewState.ShowName(data.users[0].name)
        else -> super.processDataState(data)
    }

    override fun processErrorState(error: UserDataState): MainViewState = when (error) {
        is UserDataState.OnUserListEmptyError -> MainViewState.OnError("User list is empty")
        else -> super.processErrorState(error)
    }

    override fun unknownError(): MainViewState = MainViewState.OnError("user name loading error")

    fun getUserName(groupId: String) = (interactor as LoadUsersInteractor).getUserList(groupId)

    fun getUserSurname(groupId: String) =
        (interactor as LoadUsersInteractor).getUserSurname(groupId)
}
