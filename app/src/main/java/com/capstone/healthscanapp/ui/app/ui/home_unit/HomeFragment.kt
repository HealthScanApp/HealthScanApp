package com.capstone.healthscanapp.ui.app.ui.home_unit

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.capstone.healthscanapp.R
import com.capstone.healthscanapp.adapter.CarouselSlideAdapter
import com.capstone.healthscanapp.databinding.FragmentHomeBinding
import com.capstone.healthscanapp.local.pref.PrefsManager
import com.capstone.healthscanapp.ui.app.login.LoginActivity
import com.capstone.healthscanapp.ui.app.ui.home_main.NotifikasiActivity
import com.capstone.healthscanapp.ui.app.ui.home_menu.CatatanKonsumsiActivity
import com.capstone.healthscanapp.ui.app.ui.home_menu.KonsultasiActivity
import com.capstone.healthscanapp.ui.app.ui.home_menu.TesKesehatanActivity
import com.capstone.healthscanapp.ui.app.ui.home_menu.TokoBergiziActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var prefsManager: PrefsManager
    private lateinit var carouselViewPager: ViewPager2
    private lateinit var carouselCardView: CardView
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    private val imageList = listOf(
        R.drawable.slide1,
        R.drawable.slide2,
        R.drawable.slide3
    )

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    private val delay = 3000L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        prefsManager = PrefsManager(requireContext())
        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()


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
            val intent = Intent(requireContext(), NotifikasiActivity::class.java)
            startActivity(intent)
        }


        carouselViewPager = binding.root.findViewById(R.id.carouselViewPager)
        carouselCardView = binding.root.findViewById(R.id.carouselCardView)

        carouselViewPager.adapter = CarouselSlideAdapter(this, imageList)

        // Start auto-scrolling
        startAutoScroll()

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Check apakah pengguna sudah masuk atau belum
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            fetchUserName(currentUser.uid)
            fetchUserIMT(currentUser.uid)
        } else {
            Toast.makeText(requireContext(), "Anda belum masuk. Harap login terlebih dahulu.", Toast.LENGTH_SHORT).show()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }
    }


    private fun fetchUserIMT(userID: String) {
        firestore.collection("users")
            .document(userID)
            .collection("imt")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val notasi = document.getString("notasi")
                    if (notasi != null) {
                        binding.tvIndextubuh.text = notasi
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.e("fetchUserIMT", "Gagal mendapatkan data IMT", exception)
            }
    }


    private fun startAutoScroll() {
        runnable = Runnable {
            val currentItem = carouselViewPager.currentItem
            val nextItem = if (currentItem == imageList.size - 1) 0 else currentItem + 1
            carouselViewPager.currentItem = nextItem
            startAutoScroll()
        }

        handler.postDelayed(runnable, delay)
    }

    override fun onDestroyView() {
        // Stop auto-scrolling when the view is destroyed
        handler.removeCallbacks(runnable)
        super.onDestroyView()
    }

    private fun fetchUserName(userID: String) {
        firestore.collection("users")
            .document(userID)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    // Dokumen pengguna ditemukan
                    val userName = document.getString("name")
                    binding.userName.text = userName
                } else {
                    Log.d("fetchUserName", "Dokumen pengguna tidak ditemukan")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("fetchUserName", "Gagal mendapatkan data pengguna", exception)
            }
    }


}


