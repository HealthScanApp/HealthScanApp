package com.capstone.healthscanapp.ui.app.ui.home_unit

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.capstone.healthscanapp.R
import com.capstone.healthscanapp.adapter.CarouselSlideAdapter
import com.capstone.healthscanapp.databinding.FragmentHomeBinding
import com.capstone.healthscanapp.ui.app.ui.home_main.NotifikasiActivity
import com.capstone.healthscanapp.ui.app.ui.home_menu.CatatanKonsumsiActivity
import com.capstone.healthscanapp.ui.app.ui.home_menu.KonsultasiActivity
import com.capstone.healthscanapp.ui.app.ui.home_menu.TesKesehatanActivity
import com.capstone.healthscanapp.ui.app.ui.home_menu.TokoBergiziActivity

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var carouselViewPager: ViewPager2
    private lateinit var carouselCardView: CardView
    private val imageList = listOf(
        R.drawable.slide1,
        R.drawable.slide2,
        R.drawable.slide3
        // Add more slides as needed
    )

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    private val delay = 3000L // Delay in milliseconds between each slide

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.btnCatatanKonsumsi.setOnClickListener {
            startActivity(Intent(requireContext(), CatatanKonsumsiActivity::class.java))
        }

        binding.btnKonsultasi.setOnClickListener {
            startActivity(Intent(requireContext(), KonsultasiActivity::class.java))
        }

        binding.btnTokoBergizi.setOnClickListener {
            startActivity(Intent(requireContext(), TokoBergiziActivity::class.java))
        }

        binding.btnTesKesehatan.setOnClickListener {
            startActivity(Intent(requireContext(), TesKesehatanActivity::class.java))
        }

        binding.btnNotifikasi.setOnClickListener {
            val intent = Intent(requireContext(), NotifikasiActivity::class.java)
            startActivity(intent)
        }

        carouselViewPager = binding.root.findViewById(R.id.carouselViewPager)
        carouselCardView = binding.root.findViewById(R.id.carouselCardView)

        carouselViewPager.adapter = CarouselSlideAdapter(this, imageList)

        // Start auto-scrolling
        startAutoScroll()

        return binding.root
    }

    private fun startAutoScroll() {
        runnable = Runnable {
            val currentItem = carouselViewPager.currentItem
            val nextItem = if (currentItem == imageList.size - 1) 0 else currentItem + 1
            carouselViewPager.currentItem = nextItem
            startAutoScroll()
        }

        handler.postDelayed(runnable, delay)
    }

    override fun onDestroyView() {
        // Stop auto-scrolling when the view is destroyed
        handler.removeCallbacks(runnable)
        super.onDestroyView()
    }
}
