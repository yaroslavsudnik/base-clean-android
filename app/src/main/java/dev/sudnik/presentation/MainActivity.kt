package dev.sudnik.presentation

import android.os.Bundle
import android.view.View
import dev.sudnik.basecleanandroid.R
import dev.sudnik.basecleanandroid.presentation.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewState, MainViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user_bt.setOnClickListener { onLoadUserName() }
        author_bt.setOnClickListener { onLoadAuthorName() }
    }

    override fun instanceViewModel(): MainViewModel {
        return MainViewModel(application)
    }

    override fun processDataState(dataState: MainViewState) {
        when (dataState) {
            is MainViewState.ShowName -> showName(dataState.name)
            else -> processDataErrorState(dataState)
        }
    }

    override fun processDataErrorState(dataErrorState: MainViewState) {
        when (dataErrorState) {
            is MainViewState.OnError -> showError(dataErrorState.message)
            else -> super.processDataErrorState(dataErrorState)
        }
    }

    override fun showLoading() {
        loading_pb.visibility = View.VISIBLE
        user_bt.isEnabled = false
        author_bt.isEnabled = false
    }

    override fun hideLoading() {
        loading_pb.visibility = View.GONE
        user_bt.isEnabled = true
        author_bt.isEnabled = true
    }

    private fun showName(name: String) {
        hideLoading()
        name_tv.text = name
    }

    private fun onLoadUserName() {
        showLoading()
        baseViewModel.getUserName()
    }

    private fun onLoadAuthorName() {
        showLoading()
        baseViewModel.getAuthorName()
    }
}
