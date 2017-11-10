package com.brastlewar.kotlin.ui.detail

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.brastlewar.kotlin.api.Endpoints

/**
 * Created by nicolas on 11/7/17.
 */
class DetailActivity : AppCompatActivity() {

    companion object {
        fun launchActivity(activity: AppCompatActivity) {

            val intent = Intent(activity, DetailActivity::class.java)
            activity.startActivity(intent)

        }
    }

}