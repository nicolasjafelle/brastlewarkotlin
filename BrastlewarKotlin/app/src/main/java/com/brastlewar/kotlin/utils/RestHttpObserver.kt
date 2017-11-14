package com.brastlewar.kotlin.utils

import com.brastlewar.kotlin.mvp.BasePresenter
import com.brastlewar.kotlin.mvp.RestHttpView
import com.brastlewar.kotlin.mvp.ViewState
import retrofit2.HttpException
import rx.Observer
import java.net.UnknownHostException

/**
 * Created by nicolas on 11/9/17.
 */
abstract class RestHttpObserver<T>(val presenter: BasePresenter<out RestHttpView>) : Observer<T> {


//    private var presenter: BasePresenter<out RestHttpView> = currentPresenter

    override fun onError(throwable: Throwable?) {

        when (throwable) {
            is HttpException -> {
                try {
                    val response = throwable.response()?.errorBody()?.string()
                    val errorCode = throwable.code()
                    onHttpErrorCode(errorCode, response)

                } catch (exc: Exception) {
                    exc.printStackTrace()
                    onUnknownError(exc)
                }
            }
            is UnknownHostException -> onHostUnreachable()
            else -> {
                if (throwable != null) {
                    onUnknownError(throwable)
                    throwable.printStackTrace()
                }
            }
        }
        onCompleted()
    }

    private fun onUnknownError(e: Throwable) {
//        if (presenter != null) {
        presenter.mvpView?.onError(e)
        presenter.setCurrentState(ViewState.State.ERROR)
//        }
    }

    private fun onHostUnreachable() {
//        if (presenter != null) {
        presenter.mvpView?.onHostUnreachable()
        presenter.setCurrentState(ViewState.State.ERROR)
//        }
    }

    private fun onHttpErrorCode(errorCode: Int, response: String?) {
//        if (presenter != null) {
        presenter.mvpView?.onHttpErrorCode(errorCode, response)
        presenter.setCurrentState(ViewState.State.ERROR)
//        }
    }

    override fun onCompleted() {

    }

}