package com.capstone.healthscanapp.ui.app.custome_view

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.capstone.healthscanapp.R

class EyeIconView : AppCompatImageView {
    private var isOpen = false
    private lateinit var openEyeDrawable: Drawable
    private lateinit var closedEyeDrawable: Drawable
    private lateinit var encryptedTransformation: PasswordTransformationMethod

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
        openEyeDrawable = context.getDrawable(R.drawable.ic_eye_open)!!
        closedEyeDrawable = context.getDrawable(R.drawable.ic_eye_closed)!!
        encryptedTransformation = PasswordTransformationMethod.getInstance()
        setImageDrawable(closedEyeDrawable)
    }

    fun setEncryptedTransformation(transformation: PasswordTransformationMethod) {
        encryptedTransformation = transformation
    }

    fun toggleEyeState() {
        isOpen = !isOpen
        updateEyeIcon()
    }

    fun updateEyeIcon() {
        setImageDrawable(if (isOpen) openEyeDrawable else closedEyeDrawable)
    }

    fun getPasswordTransformation(): PasswordTransformationMethod? {
        return if (isOpen) {
            PasswordTransformationMethod.getInstance()
        } else {
            null
        }
    }
}