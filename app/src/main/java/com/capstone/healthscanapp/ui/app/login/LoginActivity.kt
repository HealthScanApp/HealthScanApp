package com.capstone.healthscanapp.ui.app.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.healthscanapp.databinding.ActivityLoginBinding
import com.capstone.healthscanapp.ui.app.ui.home_main.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        binding.registerPromptTextView.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.googleRegisterButton.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignGoogleActivity::class.java)
            startActivity(intent)
        }

        binding.logInButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                binding.emailEditText.error = "Jangan Kosong"
                binding.passwordEditText.error = "Jangan Kosong"
            } else {
                // Sign in user with email and password
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = firebaseAuth.currentUser
                            if (user != null && user.isEmailVerified) {

                                // Fetch additional user data from Firestore
                                firestore.collection("users")
                                    .document(user.uid)
                                    .get()
                                    .addOnSuccessListener { document ->
                                        // Get user data and proceed to home activity
                                        val userData = document.data
                                        // Do something with userData if needed
                                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(baseContext, "Error fetching user data: $e",
                                            Toast.LENGTH_SHORT).show()
                                    }
                            } else {
                                Toast.makeText(baseContext, "Tolong verifikasi email anda, sebelum login",
                                    Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            binding.emailEditText.error = "Email salah"
                            binding.passwordEditText.error = "Password salah"
                        }
                    }
            }
        }
    }
}
