package dev.sudnik.domain.state

import dev.sudnik.domain.model.User

sealed class UserDataState {
    class UserListLoaded(val users: ArrayList<User>) : UserDataState() {
        companion object {
            fun create(): UserListLoaded = UserListLoaded(arrayListOf())
        }
    }

    class UserLoaded(val user: User) : UserDataState() {
        companion object {
            fun create(): UserLoaded = UserLoaded(User::class.java.newInstance())
        }
    }

    class SurnameLoaded(val surname: String) : UserDataState() {
        companion object {
            fun create(): SurnameLoaded = SurnameLoaded("")
        }
    }

    object OnUserListEmptyError : UserDataState()
    class OnUserError(val message: String) : UserDataState()
}
