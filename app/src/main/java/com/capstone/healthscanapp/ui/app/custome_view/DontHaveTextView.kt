package com.capstone.healthscanapp.ui.app.custome_view

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.capstone.healthscanapp.R

class DontHaveTextView : androidx.appcompat.widget.AppCompatTextView {
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
        // Set custom font (replace "your_custom_font.ttf" with the actual font file)
        val customFont = ResourcesCompat.getFont(context, R.font.roboto_medium)
        typeface = customFont

        // Set text color
        setTextColor(ContextCompat.getColor(context, R.color.custom_text_color))

        // Set text size
        textSize = resources.getDimension(R.dimen.custom_text_size)

        // Add a subtle fade-in animation
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alpha = 0f
            animate().alpha(1f).setDuration(500).start()
        }

        // Center the text horizontally
        gravity = Gravity.CENTER_HORIZONTAL
    }
}