package com.brastlewar.kotlin.extensions

import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window

/**
 * Created by nicolas on 11/8/17.
 */

fun AppCompatActivity.launchSceneTransitionAnimation(intent: Intent, view: View?) {
    val options: ActivityOptionsCompat

    if (view == null) {
        options = ActivityOptionsCompat.makeBasic()
    } else {
        if (ViewCompat.getTransitionName(view) == null
                || ViewCompat.getTransitionName(view).isEmpty()) {
            ViewCompat.setTransitionName(view, this.getString(com.brastlewar.kotlin.R.string.transition_name))
        }

        val pairList: ArrayList<Pair<View, String>> = ArrayList()

        if (this.findViewById<View>(android.R.id.statusBarBackground) != null) {
            pairList.add(Pair.create(this.findViewById(android.R.id.statusBarBackground),
                    Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME))
        }

        if (this.findViewById<View>(android.R.id.navigationBarBackground) != null) {
            pairList.add(Pair.create(this.findViewById(android.R.id.navigationBarBackground),
                    Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME))
        }

        pairList.add(Pair.create(view, ViewCompat.getTransitionName(view)))

        options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, *pairList.toTypedArray())
    }

    ActivityCompat.startActivity(this, intent, options.toBundle())
}


