package com.brastlewar.kotlin.ui.main

import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.brastlewar.kotlin.domain.Citizen
import com.brastlewar.kotlin.ui.AbstractAppCompatActivity

/**
 * Created by nicolas on 11/7/17.
 */
class MainActivity : AbstractAppCompatActivity(), MainFragment.Callback {

    companion object {
        fun launchActivity(activity: AppCompatActivity) {

            val intent = Intent(activity, MainActivity::class.java)
            ActivityCompat.startActivity(activity, intent, null)
        }
    }

    override fun setInitialFragment() {
        setInitialFragment(MainFragment.newInstance())
    }

    override fun onItemSelect(citizen: Citizen, view: View) {
        Toast.makeText(this, "${citizen.name} | ${citizen.age}", Toast.LENGTH_SHORT).show()
    }
}