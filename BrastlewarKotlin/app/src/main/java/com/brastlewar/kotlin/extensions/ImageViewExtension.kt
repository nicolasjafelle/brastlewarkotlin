package com.brastlewar.kotlin.extensions

import android.widget.ImageView
import com.brastlewar.kotlin.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

/**
 * Created by nicolas on 11/10/17.
 */

fun ImageView.loadImage(url: String) {

    Picasso.with(context)
            .load(url)
            .fit()
            .centerCrop()
            .error(R.drawable.ic_image_black)
            .placeholder(R.drawable.ic_image_black)
            .into(this)
}

fun ImageView.loadImage(url: String, downloaded: () -> Unit) {

    val creator = Picasso.with(context).load(url).fit().centerCrop()

    creator.into(this, object : Callback {
        override fun onSuccess() {
            downloaded()
        }

        override fun onError() {
            downloaded()
        }
    })
}
