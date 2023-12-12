package com.capstone.healthscanapp.ui.app.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.capstone.healthscanapp.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var logInButton: Button
    private lateinit var googleRegisterButton: Button
    private lateinit var registerPromptTextView: TextView
    private lateinit var forgotPasswordTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        logInButton = findViewById(R.id.logInButton)
        googleRegisterButton = findViewById(R.id.googleRegisterButton)
        registerPromptTextView = findViewById(R.id.registerPromptTextView)
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView)

        // aksi untuk tombol login
        logInButton.setOnClickListener {

        }

        // aksi untuk tombol Google
        googleRegisterButton.setOnClickListener {

        }

        // aksi untuk teks "Don't have an account? Sign Up"
        registerPromptTextView.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        // aksi untuk teks "Forgot Password?"
        forgotPasswordTextView.setOnClickListener {

        }
    }
}


