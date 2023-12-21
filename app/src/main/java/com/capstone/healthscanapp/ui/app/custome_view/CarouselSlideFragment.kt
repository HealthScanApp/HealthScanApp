package com.capstone.healthscanapp.ui.app.custome_view

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.capstone.healthscanapp.R

class CarouselSlideFragment(private val imageResId: Int) : Fragment(R.layout.fragment_carousel_slide) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageView: ImageView = view.findViewById(R.id.carouselSlideImageView)
        imageView.setImageResource(imageResId)
    }
}
