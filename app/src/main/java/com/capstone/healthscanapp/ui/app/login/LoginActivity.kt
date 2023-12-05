package com.capstone.healthscanapp.ui.app.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.healthscanapp.databinding.ActivityLoginBinding
import com.capstone.healthscanapp.ui.app.home.home_main.HomeActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

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
                // Handle empty fields
                binding.emailEditText.error = "Jangan Kosong"
                binding.passwordEditText.error = "Jangan Kosong"
            } else {
                // Sign in user with email and password
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Check if the user's email is verified
                            val user = firebaseAuth.currentUser
                            if (user != null && user.isEmailVerified) {
                                // If email is verified, proceed to home activity
                                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                // If email is not verified, display a message
                                Toast.makeText(baseContext, "Tolong verifikasi email anda, sebelum login",
                                    Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            // If sign-in fails, display a message to the user.
                            binding.emailEditText.error = "Email salah"
                            binding.passwordEditText.error = "Password salah"
                        }
                    }
            }
        }
    }
}


