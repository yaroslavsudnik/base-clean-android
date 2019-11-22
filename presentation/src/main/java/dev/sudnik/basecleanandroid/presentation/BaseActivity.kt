package dev.sudnik.basecleanandroid.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import dev.sudnik.basecleanandroid.presentation.State.*
import dev.sudnik.basecleanandroid.presentation.State.DefaultError.DefaultUnknownError
import dev.sudnik.basecleanandroid.presentation.State.DefaultError.ServerToNativeError

abstract class BaseActivity<StateType, ViewModel : BaseViewModel<StateType>> : AppCompatActivity() {

    abstract fun getLayoutId(): Int
    abstract fun instanceViewModel(): ViewModel
    abstract fun processDataState(dataState: StateType)
    abstract fun showLoading()
    abstract fun hideLoading()

    lateinit var baseViewModel: ViewModel

    open fun processDataErrorState(dataErrorState: StateType) {
        Log.e(TAG, "View state object ${dataErrorState.toString()} has not been determined")
        processDefaultErrorState(DefaultUnknownError())
    }

    open fun showError(message: String) = Log.e(TAG, message)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

        baseViewModel = getViewModel()
        baseViewModel.state.observe(::getLifecycle, ::processState)
        baseViewModel.errorState.observe(::getLifecycle, ::processState)

        hideLoading()
    }

    private fun processState(state: State<StateType>?) {
        when (state) {
            Loading -> showLoading()
            is Render -> processDataState(state.dataState)
            is DefaultError -> processDefaultErrorState(state)
        }
    }

    private fun processDefaultErrorState(error: DefaultError<StateType>) {
        when (error) {
            is ServerToNativeError -> processState(Render(error.nativeError))
            else -> showError("unknown nativeError")
        }
        hideLoading()
    }

    private fun getViewModel(): ViewModel =
        ViewModelProviders.of(this).get(instanceViewModel().javaClass)

    companion object {
        private val TAG = this::class.java.simpleName
    }
}
