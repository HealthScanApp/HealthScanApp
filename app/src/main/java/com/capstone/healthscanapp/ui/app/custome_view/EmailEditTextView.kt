package com.capstone.healthscanapp.ui.app.custome_view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.capstone.healthscanapp.R


class EmailEditTextView : AppCompatEditText {

    private lateinit var emailIconDrawable: Drawable

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
        emailIconDrawable = ContextCompat.getDrawable(context, R.drawable.email_24) as Drawable
        inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        compoundDrawablePadding = 16

        setHint(R.string.hint_email)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setAutofillHints(AUTOFILL_HINT_EMAIL_ADDRESS)
        }
        setDrawable(emailIconDrawable)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    error = context.getString(R.string.alert_email_required)
                    setTextColor(Color.RED)
                } else if (!Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                    error = context.getString(R.string.alert_email_error)
                    setTextColor(Color.RED)
                } else if (s.contains("@example.com")) {
                    error = context.getString(R.string.alert_email_example_com_not_allowed)
                    setTextColor(Color.RED)
                } else {
                    error = null
                    setTextColor(Color.BLACK)
                }
            }

        })
    }

    private fun setDrawable(
        start: Drawable? = null,
        top: Drawable? = null,
        end: Drawable? = null,
        bottom: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(start, top, end, bottom)
    }
}