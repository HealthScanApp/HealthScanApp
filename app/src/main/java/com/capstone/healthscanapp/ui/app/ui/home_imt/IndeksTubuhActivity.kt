package com.capstone.healthscanapp.ui.app.ui.home_imt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.capstone.healthscanapp.R
import com.capstone.healthscanapp.databinding.ActivityIndeksTubuhBinding
import com.capstone.healthscanapp.ui.app.ui.home_main.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class IndeksTubuhActivity : AppCompatActivity() {

    lateinit var binding : ActivityIndeksTubuhBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var currentUserID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIndeksTubuhBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        firestore = FirebaseFirestore.getInstance()
        currentUserID = FirebaseAuth.getInstance().currentUser?.uid ?: ""


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
                imt in 18.5..22.9 -> getString(R.string.hasil_normal)
                imt in 23.0..29.9 -> getString(R.string.hasil_indikasi)
                else -> getString(R.string.hasil_obesitas)
            }

            val imtData = hashMapOf(
                "tinggi_badan" to tinggiBadanCm,
                "berat_badan" to beratBadanKg,
                "imt" to imt,
                "notasi" to notasi,
            )

            if (currentUserID.isNotEmpty()) {
                firestore.collection("users")
                    .document(currentUserID)
                    .collection("imt")
                    .add(imtData)
                    .addOnSuccessListener {documentReference ->
                        showToast("Data IMT berhasil disimpan")
                        navigateToHomeActivity()
                    }
                    .addOnFailureListener { e ->
                        showToast("Gagal menyimpan data IMT: ${e.message}")
                    }
            }

            val hasilLengkap = "$hasilIMT\n$notasi"
            binding.edtHasil.text = hasilLengkap
        } else {
            binding.edtHasil.text = getString(R.string.pesan_error)
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }



}