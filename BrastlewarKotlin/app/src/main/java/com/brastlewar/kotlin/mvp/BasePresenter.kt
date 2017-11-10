package com.brastlewar.kotlin.mvp

import rx.subscriptions.CompositeSubscription

/**
 * Created by nicolas on 11/9/17.
 */
open class BasePresenter<T : MvpView> : Presenter<T> {


    var mvpView: T? = null

    protected var compositeSubscription: CompositeSubscription? = null

    protected lateinit var viewState: ViewState

    override fun attachMvpView(mvpView: T) {
        this.mvpView = mvpView
        this.compositeSubscription = CompositeSubscription()
        viewState = ViewState()
    }

    override fun detachMvpView() {
        this.mvpView = null

        if (this.compositeSubscription != null) {
            this.compositeSubscription?.clear()
            this.compositeSubscription?.unsubscribe()
        }
    }

    fun isViewAttached() = this.mvpView != null


    fun setCurrentState(newState: ViewState.State) {
        this.viewState.currentState = newState
    }

    fun isIdle() = this.viewState.currentState != ViewState.State.FINISH &&
            this.viewState.currentState == ViewState.State.IDLE

    fun isFinished() = this.viewState.currentState !== ViewState.State.ERROR &&
            this.viewState.currentState !== ViewState.State.IDLE &&
            this.viewState.currentState !== ViewState.State.LOADING &&
            this.viewState.currentState === ViewState.State.FINISH


    fun hasFailed() = this.viewState.currentState !== ViewState.State.FINISH &&
                this.viewState.currentState !== ViewState.State.IDLE &&
                this.viewState.currentState !== ViewState.State.LOADING &&
                this.viewState.currentState === ViewState.State.ERROR


}