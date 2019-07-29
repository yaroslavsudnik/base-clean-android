package dev.sudnik.basecleanandroid.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import dev.sudnik.basecleanandroid.presentation.State.*
import dev.sudnik.basecleanandroid.presentation.State.DefaultError.*

abstract class BaseActivity<StateType, ViewModel : BaseViewModel<StateType>> :
    AppCompatActivity() {

    companion object {
        private val TAG = this::class.java.simpleName
    }

    abstract fun getLayoutId(): Int
    abstract fun instanceViewModel(): ViewModel
    abstract fun processDataState(dataState: StateType)
    abstract fun showLoading()
    abstract fun hideLoading()

    open fun processDataErrorState(dataErrorState: StateType) {
        Log.e(TAG, "View state object ${dataErrorState.toString()} has not been determined")
        processDefaultErrorState(DefaultUnknownError())
    }

    lateinit var baseViewModel: ViewModel

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
            is Render -> processRenderState(state.dataState)
            is DefaultError -> processDefaultErrorState(state)
        }
    }

    private fun processRenderState(render: StateType) {
        when (render) {
            baseViewModel.unknownError() -> processDefaultErrorState(DefaultUnknownError())
            else -> processDataState(render)
        }
    }

    private fun processDefaultErrorState(error: DefaultError<StateType>) {
        when (error) {
            is ServerToNativeError -> processState(
                Render(error.nativeError)
            )
            else -> showError(error)
        }
        hideLoading()
    }

    private fun showError(defaultError: DefaultError<StateType>) {
        val message = when (defaultError) {
            is DefaultAuthError -> "auth nativeError"
            is DefaultNotFound -> "not found nativeError"
            else -> "unknown nativeError"
        }
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
            .show()
        hideLoading()
    }

    fun showError(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
            .show()
        hideLoading()
    }

    private fun getViewModel(): ViewModel =
        ViewModelProviders.of(this).get(instanceViewModel().javaClass)
}