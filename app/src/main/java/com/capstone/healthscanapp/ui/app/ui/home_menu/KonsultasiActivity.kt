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

        val adapter = ConsultationItemAdapter(consultationItems) { item ->
            showPopupMessage(item)
        }

        recyclerView.adapter = adapter

        val backButton: ImageView = findViewById(R.id.icon_back)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showPopupMessage(item: ConsultationItem) {
        val builder = AlertDialog.Builder(this)
        val view = LayoutInflater.from(this).inflate(R.layout.popup_message_fitur, null)
        builder.setView(view)
        val alertDialog = builder.create()

        val message = view.findViewById<TextView>(R.id.popupMessage)
        message.text = "Maaf, fitur ini masih dalam pengembangan."

        val closeButton = view.findViewById<Button>(R.id.closeButton)
        closeButton.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }
}