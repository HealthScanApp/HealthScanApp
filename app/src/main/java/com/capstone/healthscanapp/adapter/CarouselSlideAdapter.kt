package com.capstone.healthscanapp.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.capstone.healthscanapp.ui.app.custome_view.CarouselSlideFragment

class CarouselSlideAdapter(fragment: Fragment, private val imageList: List<Int>) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = imageList.size

    override fun createFragment(position: Int): Fragment {
        return CarouselSlideFragment(imageList[position])
    }
}
