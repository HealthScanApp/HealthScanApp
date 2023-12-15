package com.capstone.healthscanapp.ui.app.custome_view

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.capstone.healthscanapp.R

class ButtonBeliView : AppCompatButton {
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
        // Ganti warna gradien sesuai keinginan Anda
        val colors = intArrayOf(
            ContextCompat.getColor(context, R.color.gradient_merah_start),
            ContextCompat.getColor(context, R.color.gradient_merah_end)
        )

        // Buat objek GradientDrawable
        val gradientDrawable = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors)

        // Set corner radius untuk sudut yang membulat
        gradientDrawable.cornerRadius = resources.getDimension(R.dimen.button_corner_radius)

        // Set drawable latar belakang untuk tombol
        background = gradientDrawable

        // Atur warna teks
        setTextColor(ContextCompat.getColor(context, R.color.white))
    }
}
