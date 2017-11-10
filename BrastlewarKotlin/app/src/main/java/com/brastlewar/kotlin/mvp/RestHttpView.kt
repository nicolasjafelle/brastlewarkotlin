package com.brastlewar.kotlin.mvp

/**
 * Created by nicolas on 11/9/17.
 */
interface RestHttpView: MvpView {

    fun onHostUnreachable()

    fun onHttpErrorCode(errorCode: Int, message: String?)
}