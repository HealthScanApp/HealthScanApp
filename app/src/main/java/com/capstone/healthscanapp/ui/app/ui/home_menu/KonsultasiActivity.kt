package com.capstone.healthscanapp.ui.app.ui.home_menu

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.healthscanapp.R
import com.capstone.healthscanapp.adapter.ConsultationItemAdapter
import com.capstone.healthscanapp.data.ConsultationItem

class KonsultasiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_konsultasi)

        // Your existing code for initializing RecyclerView and adapter
        val consultationItems = listOf(
            ConsultationItem(
                "Dokter Rijal, Sp.JP",
                "Spesialis Jantung",
                "Tap to Chat"
            ),
            ConsultationItem(
                "Dokter Syawal, Sp.PD-KEMD",
                "Spesialis Diabetes",
                "Tap to Chat"
            )
        )

        val recyclerView: RecyclerView = findViewById(R.id.productsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ConsultationItemAdapter(consultationItems)

        // Add back button functionality with pop-up message
        val backButton: ImageView = findViewById(R.id.icon_back)
        backButton.setOnClickListener {
            showPopupMessage()
        }
    }

    private fun showPopupMessage() {
        val builder = AlertDialog.Builder(this)
        val view = LayoutInflater.from(this).inflate(R.layout.popup_message_fitur, null)
        builder.setView(view)
        val alertDialog = builder.create()

        // Set a message in the popup
        val message = view.findViewById<TextView>(R.id.popupMessage)
        message.text = "Maaf, fitur ini masih dalam pengembangan."

        // Add a button to close the popup
        val closeButton = view.findViewById<Button>(R.id.closeButton)
        closeButton.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }
}
