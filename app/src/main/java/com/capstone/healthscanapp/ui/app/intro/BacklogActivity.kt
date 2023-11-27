package com.capstone.healthscanapp.ui.app.intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.capstone.healthscanapp.R
import com.capstone.healthscanapp.databinding.ActivityBacklogBinding
import com.capstone.healthscanapp.databinding.ActivitySplashBinding
import com.capstone.healthscanapp.local.pref.PrefsManager
import com.capstone.healthscanapp.ui.app.home.HomeActivity
import com.capstone.healthscanapp.ui.app.login.LoginActivity

class BacklogActivity : AppCompatActivity() {

    private lateinit var prefsManager: PrefsManager
    private lateinit var binding : ActivityBacklogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBacklogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        prefsManager = PrefsManager(this)
        val btnStart = findViewById<Button>(R.id.btn_start)
        btnStart.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            prefsManager.isExampleLogin = true
            finish()
        }
    }
}