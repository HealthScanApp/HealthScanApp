package com.capstone.healthscanapp.ui.app.home.home_camera

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.healthscanapp.R
import com.capstone.healthscanapp.databinding.ActivityCameraBinding
import com.capstone.healthscanapp.databinding.ActivityHomeBinding

class CameraActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCameraBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }
}