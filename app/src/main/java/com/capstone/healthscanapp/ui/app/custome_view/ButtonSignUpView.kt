package com.capstone.healthscanapp.ui.app.custome_view

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.capstone.healthscanapp.R

class ButtonSignUpView : AppCompatButton {
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

        // Create a gradient drawable
        val gradientDrawable = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors)

        // Set corner radius for rounded corners
        gradientDrawable.cornerRadius =
            resources.getDimension(R.dimen.button_corner_radius)

        // Set the background drawable for the button
        background = gradientDrawable

        // Set text color
        setTextColor(ContextCompat.getColor(context, R.color.white))
    }
}
