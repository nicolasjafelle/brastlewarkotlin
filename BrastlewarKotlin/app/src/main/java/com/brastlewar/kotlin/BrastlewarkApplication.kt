package com.brastlewar.kotlin

import android.app.Application

/**
 * Created by nicolas on 11/7/17.
 */
class BrastlewarkApplication : Application() {

    companion object {
        lateinit var instance: BrastlewarkApplication

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}