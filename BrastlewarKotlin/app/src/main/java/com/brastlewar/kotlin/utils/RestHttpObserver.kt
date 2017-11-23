package com.brastlewar.kotlin.utils

import com.brastlewar.kotlin.mvp.BasePresenter
import com.brastlewar.kotlin.mvp.RestHttpView
import com.brastlewar.kotlin.mvp.ViewState
import retrofit2.HttpException
import rx.Observer
import java.net.UnknownHostException


@Deprecated("THIS CLASS NEEDS TO BE MIGRATED TO USE WITH KOTLIN COROUTINES")
abstract class RestHttpObserver<T>(private val presenter: BasePresenter<out RestHttpView>) : Observer<T> {


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
        presenter.mvpView?.onError(e)
        presenter.setCurrentState(ViewState.State.ERROR)
    }

    private fun onHostUnreachable() {
        presenter.mvpView?.onHostUnreachable()
        presenter.setCurrentState(ViewState.State.ERROR)
    }

    private fun onHttpErrorCode(errorCode: Int, response: String?) {
        presenter.mvpView?.onHttpErrorCode(errorCode, response)
        presenter.setCurrentState(ViewState.State.ERROR)
    }

    override fun onCompleted() {

    }

}