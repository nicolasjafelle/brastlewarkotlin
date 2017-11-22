package com.brastlewar.kotlin.mvp

import rx.subscriptions.CompositeSubscription

/**
 * Created by nicolas on 11/9/17.
 */
open class BasePresenter<T : MvpView> : Presenter<T> {


    var mvpView: T? = null

//    protected var compositeSubscription: CompositeSubscription? = null

    protected lateinit var viewState: ViewState

    override fun attachMvpView(mvpView: T) {
        this.mvpView = mvpView
//        compositeSubscription = CompositeSubscription()
        viewState = ViewState()
    }

    override fun detachMvpView() {
        mvpView = null

//        if (compositeSubscription != null) {
//            compositeSubscription?.clear()
//            compositeSubscription?.unsubscribe()
//        }
    }

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