package dev.sudnik.domain.interactor

import dev.sudnik.basecleanandroid.domain.BaseInteractor
import dev.sudnik.basecleanandroid.domain.DataState
import dev.sudnik.basecleanandroid.domain.ErrorResponse
import dev.sudnik.domain.entity.UserEntity
import dev.sudnik.domain.repository.Repository
import dev.sudnik.domain.state.UserDataState

class LoadUsersInteractor(private val userRepository: Repository) : BaseInteractor<UserDataState, UserEntity>() {

    fun getUserList(callback: (DataState<UserDataState>) -> Unit) {
        userRepository.getUserList(getCallback(callback, UserDataState.UserListLoaded.create()))
    }

    fun getUserName(callback: (DataState<UserDataState>) -> Unit) {
        userRepository.getUserList(getCallback(callback, UserDataState.UserLoaded.create()))
    }

    fun getUserSurname(callback: (DataState<UserDataState>) -> Unit) {
        userRepository.getUserList(getCallback(callback, UserDataState.SurnameLoaded.create()))
    }

    override fun processSuccessState(
            clazz: UserDataState,
            data: UserEntity
    ): UserDataState {
        return when (clazz) {
            is UserDataState.UserListLoaded -> UserDataState.UserListLoaded(data.users)
            is UserDataState.UserLoaded -> UserDataState.UserLoaded(data.getUser())
            is UserDataState.SurnameLoaded -> UserDataState.SurnameLoaded(data.getSurname())
            else -> throw IllegalArgumentException(
                "State object $clazz has not been determined"
            )
        }
    }

    override fun processErrorState(error: ErrorResponse): UserDataState =
        when (error.errorCode) {
            403 -> UserDataState.OnUserListEmptyError
            else -> UserDataState.OnUserError(error.message)
        }
}