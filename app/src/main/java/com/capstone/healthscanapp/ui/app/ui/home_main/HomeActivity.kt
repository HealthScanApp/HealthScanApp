package com.capstone.healthscanapp.ui.app.ui.home_main

import ProfileFragment
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.capstone.healthscanapp.R
import com.capstone.healthscanapp.databinding.ActivityHomeBinding
import com.capstone.healthscanapp.ui.app.ui.home_camera.CameraActivity
import com.capstone.healthscanapp.ui.app.ui.home_unit.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        // Inisialisasi FloatingActionButton dengan ID button_chat
        val buttonCamera = findViewById<FloatingActionButton>(R.id.button_Camera)
        bottomNavigationView = findViewById(R.id.bottom_navigation)

        buttonCamera.setOnClickListener {
            intent = Intent(this@HomeActivity, CameraActivity::class.java)
            startActivity(intent)
        }


        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.bottom_profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
        replaceFragment(HomeFragment())


    }

    private fun replaceFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit()
    }

}