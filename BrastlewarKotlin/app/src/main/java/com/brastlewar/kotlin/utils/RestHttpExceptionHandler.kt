package com.brastlewar.kotlin.utils


import com.brastlewar.kotlin.mvp.BasePresenter
import com.brastlewar.kotlin.mvp.RestHttpView
import com.brastlewar.kotlin.mvp.ViewState
import kotlinx.coroutines.experimental.CancellationException
import kotlinx.coroutines.experimental.CoroutineExceptionHandler
import kotlinx.coroutines.experimental.Job
import retrofit2.HttpException
import java.net.UnknownHostException
import kotlin.coroutines.experimental.AbstractCoroutineContextElement
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by nicolas on 11/30/17.
 */
class RestHttpExceptionHandler : CoroutineExceptionHandler, AbstractCoroutineContextElement(CoroutineExceptionHandler.Key) {


    override fun handleException(context: CoroutineContext, exception: Throwable) {
        if (exception is CancellationException) return
        if (context[Job]?.cancel(exception) == false) return
        else exception.printStackTrace()
    }

    fun handle(context: CoroutineContext, exception: Throwable, presenter: BasePresenter<out RestHttpView>) {
        handleException(context, exception)
        onError(exception, presenter)
    }


    private fun onError(throwable: Throwable?, presenter: BasePresenter<out RestHttpView>) {
        when (throwable) {
            is HttpException -> {
                try {
                    val response = throwable.response()?.errorBody()?.string()
                    val errorCode = throwable.code()
                    onHttpErrorCode(errorCode, response, presenter)

                } catch (exc: Exception) {
                    onUnknownError(exc, presenter)
                }
            }
            is UnknownHostException -> onHostUnreachable(presenter)
            else -> {
                if (throwable != null) {
                    onUnknownError(throwable, presenter)
                }
            }
        }
    }

    private fun onUnknownError(e: Throwable, presenter: BasePresenter<out RestHttpView>) {
        presenter.mvpView?.onError(e)
        presenter.setCurrentState(ViewState.State.ERROR)
    }

    private fun onHostUnreachable(presenter: BasePresenter<out RestHttpView>) {
        presenter.mvpView?.onHostUnreachable()
        presenter.setCurrentState(ViewState.State.ERROR)
    }

    private fun onHttpErrorCode(errorCode: Int, response: String?, presenter: BasePresenter<out RestHttpView>) {
        presenter.mvpView?.onHttpErrorCode(errorCode, response)
        presenter.setCurrentState(ViewState.State.ERROR)
    }

}