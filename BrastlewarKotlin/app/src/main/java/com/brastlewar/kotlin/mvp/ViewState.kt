package com.brastlewar.kotlin.mvp

/**
 * Created by nicolas on 11/9/17.
 */
class ViewState {

    internal var currentState: State = State.IDLE

    enum class State {
        LOADING,
        ERROR,
        IDLE,
        FINISH
    }
}