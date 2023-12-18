package com.capstone.healthscanapp.ui.app.ui.home_imt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.healthscanapp.R
import com.capstone.healthscanapp.databinding.ActivityIndeksTubuhBinding


class IndeksTubuhActivity : AppCompatActivity() {

    lateinit var binding : ActivityIndeksTubuhBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIndeksTubuhBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.simpanHasil.setOnClickListener {
            hitungIMT()

        }

    }

    private fun hitungIMT() {
        val tinggiBadanCm = binding.edtTextTinggi.text.toString().toDoubleOrNull()
        val beratBadanKg = binding.edtTextBadan.text.toString().toDoubleOrNull()

        if (tinggiBadanCm != null && beratBadanKg != null) {
            val tinggiBadanM = tinggiBadanCm / 100 // Konversi tinggi dari cm ke meter
            val imt = beratBadanKg / (tinggiBadanM * tinggiBadanM)

            val hasilIMT = getString(R.string.hasil_imt, imt)

            val notasi = when {
                imt < 18.5 -> getString(R.string.hasil_kurang)
                imt in 18.5..22.9 ->  getString(R.string.hasil_normal)
                imt in 23.0..29.9 -> getString(R.string.hasil_indikasi)
                else -> getString(R.string.hasil_obesitas)
            }

            val hasilLengkap = "$hasilIMT\n$notasi"
            binding.edtHasil.text = hasilLengkap
        } else {
            binding.edtHasil.text = getString(R.string.pesan_error)
        }
    }

}