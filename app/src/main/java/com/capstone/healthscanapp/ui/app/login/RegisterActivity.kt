package com.capstone.healthscanapp.ui.app.login

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.healthscanapp.R
import com.capstone.healthscanapp.databinding.ActivityRegisterBinding
import com.capstone.healthscanapp.ui.app.custome_view.EyeIconView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var eyeIcon: EyeIconView

    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        eyeIcon = findViewById(R.id.eyeIcon)
        eyeIcon.setEncryptedTransformation(PasswordTransformationMethod.getInstance())

        eyeIcon.setOnClickListener {

            isPasswordVisible = !isPasswordVisible
            togglePasswordVisibility()
        }

        binding.alreadyHaveAccountTextView.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.googleRegisterButton.setOnClickListener {
            val intent = Intent(this@RegisterActivity, SignGoogleActivity::class.java)
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
                showLoading(true)

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        showLoading(false)
                        if (task.isSuccessful) {

                            val user = hashMapOf(
                                "name" to name,
                                "email" to email,
                                "password" to password

                            )
                            firestore.collection("users")
                                .document(firebaseAuth.currentUser?.uid ?: "")
                                .set(user)
                                .addOnSuccessListener {

                                    sendEmailVerification()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(
                                        baseContext, "Error storing user data: $e",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        } else {

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

                    Toast.makeText(
                        baseContext, "Verifikasi email telah dikirim ke ${user.email}",
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {

                    Toast.makeText(
                        baseContext, "Gagal mengirim verifikasi email. Silakan coba lagi.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun togglePasswordVisibility() {

        isPasswordVisible = !isPasswordVisible

        val transformation = eyeIcon.getPasswordTransformation()

        binding.passwordEditText.transformationMethod = transformation

        eyeIcon.toggleEyeState()

        eyeIcon.updateEyeIcon()

        binding.passwordEditText.clearFocus()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}