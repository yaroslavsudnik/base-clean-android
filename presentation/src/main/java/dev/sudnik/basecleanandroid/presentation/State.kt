package dev.sudnik.basecleanandroid.presentation

sealed class State<out T> {
    object Loading : State<Nothing>()
    class Render<T>(val dataState: T) : State<T>()
    sealed class DefaultError<T> : State<T>() {
        class DefaultAuthError<T> : DefaultError<T>()
        class DefaultNotFound<T> : DefaultError<T>()
        class DefaultUnknownError<T> : DefaultError<T>()
        class ServerToNativeError<T>(val nativeError: T) : DefaultError<T>()
    }
}