package dev.sudnik.presentation

sealed class MainViewState {
    class ShowName(val name: String) : MainViewState()
    class OnError(val message: String) : MainViewState()
}
