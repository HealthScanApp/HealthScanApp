package com.capstone.healthscanapp.ui.app.home.home_main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.healthscanapp.R
import com.capstone.healthscanapp.databinding.ActivityHomeBinding
import com.capstone.healthscanapp.databinding.ActivityNotifikasiBinding

class NotifikasiActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNotifikasiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotifikasiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.iconBack.setOnClickListener {
            finish()
        }


    }
}