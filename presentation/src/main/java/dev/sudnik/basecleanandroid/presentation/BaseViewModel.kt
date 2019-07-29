package dev.sudnik.basecleanandroid.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.sudnik.basecleanandroid.presentation.State.DefaultError

abstract class BaseViewModel<StateType>(
    application: Application
) : AndroidViewModel(application) {

    abstract fun unknownError(): StateType
    abstract val reducers: ArrayList<BaseReducer<StateType, out Any?>>

    private val reducerList by lazy {
        reducers
    }

    val state: LiveData<State<StateType>> get() = _state

    private val _state: MutableLiveData<State<StateType>> by lazy {
        MutableLiveData<State<StateType>>()
    }

    val errorState: LiveData<DefaultError<StateType>> get() = _errorState

    private val _errorState: MutableLiveData<DefaultError<StateType>> by lazy {
        MutableLiveData<DefaultError<StateType>>()
    }

    init {
        reducerList.forEach {
            it.state = _state
            it.errorState = _errorState
        }
    }
}