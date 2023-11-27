package com.capstone.healthscanapp.ui.app.home.home_fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.healthscanapp.databinding.FragmentHomeBinding
import com.capstone.healthscanapp.ui.app.home.home_menu.CatatanKonsumsiActivity
import com.capstone.healthscanapp.ui.app.home.home_menu.KonsultasiActivity
import com.capstone.healthscanapp.ui.app.home.home_menu.TesKesehatanActivity
import com.capstone.healthscanapp.ui.app.home.home_menu.TokoBergiziActivity


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

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
            startActivity(Intent(requireContext(), TesKesehatanActivity::class.java))
        }



        return binding.root
    }
}
