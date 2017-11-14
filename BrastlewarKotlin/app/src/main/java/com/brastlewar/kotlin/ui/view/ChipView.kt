package com.brastlewar.kotlin.ui.view

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import com.brastlewar.kotlin.R

/**
 * Created by nicolas on 11/14/17.
 */
class ChipView : AppCompatTextView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        val params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)


        val margin = resources.getDimensionPixelOffset(R.dimen.base_separation)
        params.setMargins(0, 0, margin, 0)

        layoutParams = params

        val padding = resources.getDimensionPixelSize(R.dimen.base_separation)
        setPadding(padding, padding, padding, padding)
        setBackgroundResource(R.drawable.chips_drawable)

        setTextColor(ContextCompat.getColor(context, R.color.white))
    }
}