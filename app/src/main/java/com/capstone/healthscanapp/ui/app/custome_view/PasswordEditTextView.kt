package com.capstone.healthscanapp.ui.app.custome_view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.capstone.healthscanapp.R

class PasswordEditTextView : AppCompatEditText {
    private lateinit var passwordIconDrawable: Drawable
    private var isPasswordVisible = false
    private lateinit var eyeIcon: EyeIconView

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
        passwordIconDrawable = ContextCompat.getDrawable(context, R.drawable.lock_24) as Drawable
        inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
        transformationMethod = PasswordTransformationMethod.getInstance()

        eyeIcon = EyeIconView(context)
        eyeIcon.setEncryptedTransformation(PasswordTransformationMethod.getInstance())
        eyeIcon.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            updatePasswordVisibility()
        }

        setHint(R.string.hint_password)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setAutofillHints(AUTOFILL_HINT_PASSWORD)
        }

        setDrawable(passwordIconDrawable)
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty() && s.length < 8) {
                    error = context.getString(R.string.error_password)
                    setTextColor(Color.RED)
                } else {
                    error = null
                    setTextColor(Color.BLACK)
                }
            }
        })

        eyeIcon.updateEyeIcon()
    }

    private fun setDrawable(
        start: Drawable? = null,
        top: Drawable? = null,
        end: Drawable? = null,
        bottom: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(start, top, end, bottom)
    }

    private fun updatePasswordVisibility() {

        transformationMethod = if (isPasswordVisible) {
            null
        } else {
            PasswordTransformationMethod.getInstance()
        }

        transformationMethod?.let {
            setSelection(text?.length ?: 0)
            eyeIcon.updateEyeIcon()
        }

        clearFocus()
    }
}