package com.capstone.healthscanapp.ui.app.ui.home_menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.healthscanapp.R
import com.capstone.healthscanapp.adapter.RiwayatMakananAdapter
import com.capstone.healthscanapp.databinding.ActivityCatatanKonsumsiBinding
import com.capstone.healthscanapp.model.RiwayatMakanan
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CatatanKonsumsiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCatatanKonsumsiBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var riwayatMakananAdapter: RiwayatMakananAdapter
    private val historyList = mutableListOf<RiwayatMakanan>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatatanKonsumsiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        riwayatMakananAdapter = RiwayatMakananAdapter(this, historyList)
        binding.recyclerView.adapter = riwayatMakananAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.iconBack.setOnClickListener { finish() }

        // Load data from Firestore
        loadDataFromFirestore()
    }

    private fun loadDataFromFirestore() {
        val currentUserID = firebaseAuth.currentUser?.uid
        if (currentUserID != null) {
            firestore.collection("users")
                .document(currentUserID)
                .collection("riwayat_konsumsi")
                .get()
                .addOnSuccessListener { documents ->
                    historyList.clear()
                    for (document in documents) {
                        val history = document.toObject(RiwayatMakanan::class.java)
                        historyList.add(history)
                    }
                    riwayatMakananAdapter.notifyDataSetChanged()
                }
                .addOnFailureListener { e ->
                    // Handle failure to fetch data
                }
        }
    }


}
