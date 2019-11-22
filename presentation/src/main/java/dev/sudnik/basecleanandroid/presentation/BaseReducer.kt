package dev.sudnik.basecleanandroid.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import dev.sudnik.basecleanandroid.domain.BaseInteractor
import dev.sudnik.basecleanandroid.domain.DataState
import dev.sudnik.basecleanandroid.presentation.State.DefaultError
import dev.sudnik.basecleanandroid.presentation.State.DefaultError.DefaultAuthError
import dev.sudnik.basecleanandroid.presentation.State.DefaultError.DefaultUnknownError
import dev.sudnik.basecleanandroid.presentation.State.Render

abstract class BaseReducer<StateType, DataType> {

    val interactor: BaseInteractor<DataType, out Any> by lazy {
        instanceInteractor()
    }

    abstract fun instanceInteractor(): BaseInteractor<DataType, out Any>
    abstract fun unknownError(): StateType

    var state = MutableLiveData<State<StateType>>()
    var errorState = MutableLiveData<DefaultError<StateType>>()

    open fun processDataState(data: DataType): StateType = unknownObject(data)

    open fun processErrorState(error: DataType): StateType {
        Log.e(TAG, "Data state object $error has not been determined")
        return unknownError()
    }

    private fun stateListObject(data: DataState<DataType>): StateType = when (data) {
        is DataState.OnSuccess -> processDataState(data.data)
        is DataState.OnError -> processErrorState(data.error)
        else -> unknownError()
    }

    private fun processResult(data: DataState<DataType>) = when (data) {
        is DataState.OnError -> errorState.postValue(processServerErrorState(data))
        else -> state.postValue(Render(stateListObject(data)))
    }

    open fun processServerErrorState(data: DataState<DataType>): DefaultError<StateType> =
            when (data) {
                is DataState.ServerError.AuthError -> DefaultAuthError()
                else -> DefaultUnknownError()
            }

    private fun unknownObject(data: DataType): StateType = throw IllegalStateException(
            "State object $data has not been determined"
    )

    init {
        interactor.dataCallback = { processResult(it) }
    }

    companion object {
        private val TAG = this::class.java.simpleName
    }
}
