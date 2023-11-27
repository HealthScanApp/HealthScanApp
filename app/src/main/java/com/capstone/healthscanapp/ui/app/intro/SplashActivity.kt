package com.capstone.healthscanapp.ui.app.intro

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.capstone.healthscanapp.databinding.ActivitySplashBinding
import com.capstone.healthscanapp.local.pref.PrefsManager
import com.capstone.healthscanapp.ui.app.home.HomeActivity
import com.capstone.healthscanapp.ui.app.login.LoginActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


        val prefsManager = PrefsManager(this)
        lifecycleScope.launch {
            delay(2000)
            val intent = when {
                prefsManager.exampleBoolean -> {
                    Intent(this@SplashActivity, HomeActivity::class.java)
                }
                prefsManager.isExampleLogin -> {
                    Intent(this@SplashActivity, LoginActivity::class.java)
                }
                else -> {
                    Intent(this@SplashActivity, BacklogActivity::class.java)
                }
            }
            startActivity(intent)
            finish()
        }
    }
}