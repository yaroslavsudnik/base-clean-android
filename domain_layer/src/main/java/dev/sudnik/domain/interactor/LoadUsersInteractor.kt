package dev.sudnik.domain.interactor

import dev.sudnik.basecleanandroid.domain.BaseInteractor
import dev.sudnik.basecleanandroid.domain.ErrorResponse
import dev.sudnik.domain.entity.UserEntity
import dev.sudnik.domain.repository.Repository
import dev.sudnik.domain.state.UserDataState

class LoadUsersInteractor(private val repository: Repository) :
        BaseInteractor<UserDataState, UserEntity>() {

    fun getUserList() = call(UserDataState.UserListLoaded.create()) { repository.getUserList(it) }

    fun getUserName() = call(UserDataState.UserLoaded.create()) { repository.getUserList(it) }

    fun getUserSurname() = call(UserDataState.SurnameLoaded.create()) { repository.getUserList(it) }

    override fun processSuccessState(clazz: UserDataState, data: UserEntity):
            UserDataState = when (clazz) {
        is UserDataState.UserListLoaded -> UserDataState.UserListLoaded(data.users)
        is UserDataState.UserLoaded -> UserDataState.UserLoaded(data.getUser())
        is UserDataState.SurnameLoaded -> UserDataState.SurnameLoaded(data.getSurname())
        else -> throw IllegalArgumentException(
                "State object $clazz has not been determined"
        )
    }

    override fun processErrorState(error: ErrorResponse): UserDataState = when (error.errorCode) {
        403 -> UserDataState.OnUserListEmptyError
        else -> UserDataState.OnUserError(error.message)
    }
}