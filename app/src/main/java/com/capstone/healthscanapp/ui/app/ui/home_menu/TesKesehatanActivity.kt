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
import com.capstone.healthscanapp.adapter.HealthItemAdapter
import com.capstone.healthscanapp.data.HealthItem

class TesKesehatanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tes_kesehatan)

        val healthItems = listOf(
            HealthItem(
                "Tes Kesehatan Jantung",
                "Your description goes here for Tes Kesehatan Jantung.",
                R.drawable.jantung
            ),
            HealthItem(
                "Tes Kesehatan Diabetes",
                "Your description goes here for Tes Kesehatan Diabetes.",
                R.drawable.diabetes
            )
        )

        val recyclerView: RecyclerView = findViewById(R.id.productsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = HealthItemAdapter(healthItems) { item ->
            showPopupMessage(item)
        }

        recyclerView.adapter = adapter

        val backButton: ImageView = findViewById(R.id.icon_back)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showPopupMessage(item: HealthItem) {
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
