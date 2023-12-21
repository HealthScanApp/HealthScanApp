package com.capstone.healthscanapp.ui.app.custome_view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.capstone.healthscanapp.R

class ButtonLoginView : AppCompatButton {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        val colors = intArrayOf(
            ContextCompat.getColor(context, R.color.gradient_start),
            ContextCompat.getColor(context, R.color.gradient_end)
        )

        val gradientDrawable = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors)

        gradientDrawable.cornerRadius =
            resources.getDimension(R.dimen.button_corner_radius)

        background = gradientDrawable

        val colorStateList = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_pressed),
                intArrayOf(android.R.attr.state_focused),
                intArrayOf()
            ),
            intArrayOf(
                ContextCompat.getColor(context, R.color.red),
                ContextCompat.getColor(context, R.color.red),
                ContextCompat.getColor(context, R.color.gradient_end)
            )
        )

        backgroundTintList = colorStateList
        setTextColor(ContextCompat.getColor(context, R.color.white))
    }
}
