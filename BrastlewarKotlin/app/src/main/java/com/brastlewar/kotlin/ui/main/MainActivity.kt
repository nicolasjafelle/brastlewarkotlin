package com.brastlewar.kotlin.ui.main

import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.brastlewar.kotlin.ui.AbstractAppCompatActivity

/**
 * Created by nicolas on 11/7/17.
 */
class MainActivity : AbstractAppCompatActivity() {

    companion object {
        fun launchActivity(activity: AppCompatActivity) {

            val intent = Intent(activity, MainActivity::class.java)
            ActivityCompat.startActivity(activity, intent, null)
//            activity.startActivity(intent)
        }
    }

    override fun setInitialFragment() {
        setInitialFragment(MainFragment.newInstance())
    }
}