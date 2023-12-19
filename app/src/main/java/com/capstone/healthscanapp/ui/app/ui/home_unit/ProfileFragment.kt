package com.capstone.healthscanapp.ui.app.ui.home_unit

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.capstone.healthscanapp.R
import com.capstone.healthscanapp.databinding.FragmentProfileBinding
import com.capstone.healthscanapp.local.pref.PrefsManager
import com.capstone.healthscanapp.ui.app.login.LoginActivity
import com.capstone.healthscanapp.ui.app.ui.home_main.NotifikasiActivity
import com.capstone.healthscanapp.ui.app.ui.home_profile.PrivacyActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var prefsManager: PrefsManager
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var userNameTextView: TextView
    private lateinit var userEmailTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        prefsManager = PrefsManager(requireContext())

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val language = binding.root.findViewById<LinearLayout>(R.id.language)
        val notif = binding.root.findViewById<LinearLayout>(R.id.notifikasi)
        val privasi = binding.root.findViewById<LinearLayout>(R.id.ketentuan_privasi)
        val akun = binding.root.findViewById<ImageView>(R.id.btn_akunsaya)
        val edit = binding.root.findViewById<ImageView>(R.id.btn_edit)
        userNameTextView = binding.root.findViewById(R.id.userNames)
        userEmailTextView = binding.root.findViewById(R.id.text_email)
        val keluarButton = binding.root.findViewById<Button>(R.id.riwayatKonseling)

        language.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }

        notif.setOnClickListener {
            val intent = Intent(requireContext(), NotifikasiActivity::class.java)
            startActivity(intent)
        }

        privasi.setOnClickListener {
            val intent = Intent(requireContext(), PrivacyActivity::class.java)
            startActivity(intent)
        }

        akun.setOnClickListener {
            val intent = Intent(requireContext(), AkunActivity::class.java)
            startActivity(intent)
        }

        edit.setOnClickListener {
            val intent = Intent(requireContext(), EditProfileActivity::class.java)
            startActivity(intent)
        }

        keluarButton.setOnClickListener {
            prefsManager.token = ""
            prefsManager.userEmail = ""
            prefsManager.isExampleLogin = false
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

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
        } else {
            Toast.makeText(requireContext(), "Anda belum masuk. Harap login terlebih dahulu.", Toast.LENGTH_SHORT).show()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }
    }


    private fun fetchUserName(userID: String) {
        firestore.collection("users")
            .document(userID)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    // Dokumen pengguna ditemukan
                    val userName = document.getString("name")
                    userNameTextView.text = userName
                    val userEmail = document.getString("email")
                    userEmailTextView.text = userEmail
                } else {
                    Log.d("fetchUserName", "Dokumen pengguna tidak ditemukan")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("fetchUserName", "Gagal mendapatkan data pengguna", exception)
            }
    }

}
