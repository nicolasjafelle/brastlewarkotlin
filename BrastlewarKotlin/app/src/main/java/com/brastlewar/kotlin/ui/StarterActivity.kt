package com.brastlewar.kotlin.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.brastlewar.kotlin.ui.main.MainActivity

/**
 * Created by nicolas on 11/7/17.
 */
class StarterActivity : AppCompatActivity() {

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        MainActivity.launchActivity(this)
        finish()
    }
}
