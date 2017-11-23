package com.brastlewar.kotlin.ui.detail

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import com.brastlewar.kotlin.R
import com.brastlewar.kotlin.domain.Citizen
import com.brastlewar.kotlin.extensions.launchSceneTransitionAnimation
import com.brastlewar.kotlin.extensions.loadImage
import com.brastlewar.kotlin.ui.AbstractAppCompatActivity
import org.parceler.Parcels

/**
 * Created by nicolas on 11/7/17.
 */
class DetailActivity : AbstractAppCompatActivity() {

    private lateinit var imageView: ImageView

    companion object {
        fun launchActivity(activity: AppCompatActivity, citizen: Citizen, transitionView: View?) {

            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailFragment.SELECTED_CITIZEN, Parcels.wrap(citizen))
            activity.launchSceneTransitionAnimation(intent, transitionView)
        }
    }

    override fun setInitialFragment() {
        setInitialFragment(DetailFragment.newInstance(intent.extras))
    }

    override fun getBaseLayoutResId() = R.layout.activity_collapse_single_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.setBackgroundColor(Color.WHITE)
        setupToolbar()

        ActivityCompat.postponeEnterTransition(this)

        imageView = findViewById(R.id.activity_collapse_single_fragment_image)

        if (intent.extras != null) {
            val citizen = Parcels.unwrap<Citizen>(intent.extras.getParcelable(DetailFragment.SELECTED_CITIZEN))

            imageView.loadImage(citizen.thumbnail, {
                ActivityCompat.startPostponedEnterTransition(this@DetailActivity)
            })
        }
    }

    private fun setupToolbar() {
        setToolbarColor(android.R.color.transparent)
        setMaterialStatusBarColor(android.R.color.transparent)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}