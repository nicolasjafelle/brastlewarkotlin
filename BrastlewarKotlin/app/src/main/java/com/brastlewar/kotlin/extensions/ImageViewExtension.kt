package com.brastlewar.kotlin.extensions

import android.widget.ImageView
import com.brastlewar.kotlin.R
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
