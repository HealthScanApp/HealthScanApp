package com.capstone.healthscanapp.ui.app.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.capstone.healthscanapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.alreadyHaveAccountTextView.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.googleRegisterButton.setOnClickListener {
            val intent = Intent(this@RegisterActivity, SignGoogleActivity::class.java)
            startActivity(intent)
        }

        binding.RegisterButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                // Handle empty fields
                binding.emailEditText.error = "Please fill all the fields"
                binding.passwordEditText.error = "Please fill all the fields"
            } else if (password.length < 6) {
                // Handle weak password
                binding.passwordEditText.error = "Password should be at least 6 characters"
            } else {
                // Create user with email and password
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Send email verification
                            sendEmailVerification()
                        } else {
                            // If sign-up fails, display a message to the user.
                            Toast.makeText(
                                baseContext, "Authentication failed: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }

    private fun sendEmailVerification() {
        val user = firebaseAuth.currentUser
        user?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Verification email sent successfully
                    Toast.makeText(
                        baseContext, "Verification email sent. Please check your email.",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Redirect to login screen
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sending email verification fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Failed to send verification email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}
