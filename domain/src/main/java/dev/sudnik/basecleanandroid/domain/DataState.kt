package dev.sudnik.basecleanandroid.domain

sealed class DataState<StateType> {
    class OnSuccess<StateType>(val data: StateType) : DataState<StateType>()
    class OnError<StateType>(val error: StateType) : DataState<StateType>()

    sealed class ServerError<StateType> : DataState<StateType>() {
        class AuthError<StateType> : ServerError<StateType>()
        class UnknownError<StateType> : ServerError<StateType>()
    }
}
