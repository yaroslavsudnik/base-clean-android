package dev.sudnik.basecleanandroid.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import dev.sudnik.basecleanandroid.domain.DataState
import dev.sudnik.basecleanandroid.presentation.State.DefaultError
import dev.sudnik.basecleanandroid.presentation.State.DefaultError.*
import dev.sudnik.basecleanandroid.presentation.State.Render

abstract class BaseReducer<StateType, DataType> {

    companion object {
        private val TAG = this::class.java.simpleName
    }

    abstract fun unknownError(): StateType

    var state = MutableLiveData<State<StateType>>()
    var errorState = MutableLiveData<DefaultError<StateType>>()

    open fun processDataState(data: DataType): StateType = unknownObject(data)

    open fun processErrorState(error: DataType): StateType {
        Log.e(TAG, "Data state object $error has not been determined")
        return unknownError()
    }

    open fun processServerErrorState(data: DataState.ServerError<DataType>): DefaultError<StateType> =
        when (data) {
            is DataState.ServerError.AuthError -> DefaultAuthError()
            is DataState.ServerError.NotFound -> DefaultNotFound()
            is DataState.ServerError.StrangeError -> DefaultUnknownError()
            is DataState.ServerError.UnknownError -> DefaultUnknownError()
        }

    fun request(request: ((DataState<DataType>) -> Unit) -> Unit) {
        request(::setResult)
    }

    private fun stateListObject(data: DataState<DataType>): StateType = when (data) {
        is DataState.OnSuccess -> processDataState(data.data)
        is DataState.OnError -> processErrorState(data.error)
        else -> unknownError()
    }

    private fun setResult(data: DataState<DataType>) {
        when (data) {
            is DataState.ServerError -> errorState.value = processServerErrorState(data)
            else -> state.value = Render(stateListObject(data))
        }
    }

    private fun unknownObject(data: DataType): StateType = throw IllegalStateException(
        "State object $data has not been determined"
    )
}