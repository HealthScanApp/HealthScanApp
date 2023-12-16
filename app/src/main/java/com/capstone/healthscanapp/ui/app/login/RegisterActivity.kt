package com.capstone.healthscanapp.ui.app.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.healthscanapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        binding.alreadyHaveAccountTextView.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }


        binding.RegisterButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                binding.nameEditText.error = "Jangan Kosong"
                binding.emailEditText.error = "Jangan Kosong"
                binding.passwordEditText.error = "Jangan Kosong"
            } else if (password.length < 6) {
                binding.passwordEditText.error = "Password harus lebih dari 6 karakter"
            } else {
                // Create user with email and password
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Store user data in Firestore
                            val user = hashMapOf(
                                "name" to name,
                                "email" to email,
                                "password" to password
                                // Add other user data as needed
                            )
                            firestore.collection("users")
                                .document(firebaseAuth.currentUser?.uid ?: "")
                                .set(user)
                                .addOnSuccessListener {
                                    // Send email verification
                                    sendEmailVerification()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(
                                        baseContext, "Error storing user data: $e",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
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
                        baseContext, "Verifikasi email telah dikirim ke ${user.email}",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Redirect to login screen
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sending email verification fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Gagal mengirim verifikasi email. Silakan coba lagi.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}