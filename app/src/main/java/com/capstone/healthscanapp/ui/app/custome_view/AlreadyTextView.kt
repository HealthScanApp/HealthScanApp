package com.capstone.healthscanapp.ui.app.custome_view

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.capstone.healthscanapp.R

class AlreadyTextView : androidx.appcompat.widget.AppCompatTextView {
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

        val customFont = ResourcesCompat.getFont(context, R.font.roboto_medium)
        typeface = customFont

        setTextColor(ContextCompat.getColor(context, R.color.custom_text_color))

        textSize = resources.getDimension(R.dimen.custom_text_size)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alpha = 0f
            animate().alpha(1f).setDuration(500).start()
        }

        gravity = Gravity.CENTER_HORIZONTAL
    }
}