package com.capstone.healthscanapp.ui.app.login
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.healthscanapp.R
import com.capstone.healthscanapp.databinding.ActivityLoginBinding
import com.capstone.healthscanapp.local.pref.PrefsManager
import com.capstone.healthscanapp.ui.app.custome_view.EyeIconView
import com.capstone.healthscanapp.ui.app.ui.home_imt.IndeksTubuhActivity
import com.capstone.healthscanapp.ui.app.ui.home_main.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var eyeIcon: EyeIconView
    private lateinit var prefsManager: PrefsManager

    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        prefsManager = PrefsManager(this)


        eyeIcon = findViewById(R.id.eyeIcon)
        eyeIcon.setEncryptedTransformation(PasswordTransformationMethod.getInstance())

        eyeIcon.setOnClickListener {

            isPasswordVisible = !isPasswordVisible
            togglePasswordVisibility()
        }

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
                showLoading(true)

                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        showLoading(false)
                        if (task.isSuccessful) {
                            val user = firebaseAuth.currentUser
                            if (user != null && user.isEmailVerified) {

                                prefsManager.userEmail = email
                                prefsManager.isExampleLogin = true

                                val prefs = getSharedPreferences("MyPrefsf", MODE_PRIVATE)
                                val isFirstRun = prefs.getBoolean("isFirstRuns", true)

                                val intent = if (isFirstRun) {

                                    Intent(this@LoginActivity, IndeksTubuhActivity::class.java)
                                } else {

                                    Intent(this@LoginActivity, HomeActivity::class.java)
                                }

                                startActivity(intent)


                                prefs.edit().putBoolean("isFirstRuns", false).apply()
                                finish()
                            } else {

                                Toast.makeText(
                                    baseContext, "Tolong verifikasi email anda, sebelum login",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {

                            binding.emailEditText.error = "Email salah"
                            binding.passwordEditText.error = "Password salah"
                        }
                    }
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
