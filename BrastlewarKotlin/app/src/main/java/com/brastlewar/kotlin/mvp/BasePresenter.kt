package com.brastlewar.kotlin.mvp

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.cancelFutureOnCompletion

/**
 * Created by nicolas on 11/9/17.
 */
open class BasePresenter<T : MvpView> : Presenter<T> {


    var mvpView: T? = null

//    protected lateinit var list: MutableList<Deferred<Any>>

    protected lateinit var viewState: ViewState

    override fun attachMvpView(mvpView: T) {
        this.mvpView = mvpView
//        list = ArrayList(0)
        viewState = ViewState()
    }

    override fun detachMvpView() {
        mvpView = null

//        list.forEach {
//            it.cancel()
//        }
//        list.clear()
    }

//    fun add(pepe: Any) {
//        list.add(pepe as Deferred<Any>)
//
//    }

    fun isViewAttached() = mvpView != null


    fun setCurrentState(newState: ViewState.State) {
        viewState.currentState = newState
    }

    fun isLoading() = viewState.currentState === ViewState.State.LOADING


    fun isIdle() = viewState.currentState != ViewState.State.FINISH &&
            viewState.currentState == ViewState.State.IDLE

    fun isFinished() = viewState.currentState !== ViewState.State.ERROR &&
            viewState.currentState !== ViewState.State.IDLE &&
            viewState.currentState !== ViewState.State.LOADING &&
            viewState.currentState === ViewState.State.FINISH


    fun hasFailed() = viewState.currentState !== ViewState.State.FINISH &&
            viewState.currentState !== ViewState.State.IDLE &&
            viewState.currentState !== ViewState.State.LOADING &&
            viewState.currentState === ViewState.State.ERROR


}