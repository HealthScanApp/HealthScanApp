package com.capstone.healthscanapp.ui.app.ui.home_unit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.healthscanapp.databinding.ActivityAkunBinding

class AkunActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAkunBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAkunBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.iconBack.setOnClickListener {
            finish()
        }


    }
}