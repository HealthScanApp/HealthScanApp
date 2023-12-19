package com.capstone.healthscanapp.ui.app.ui.home_menu

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.capstone.healthscanapp.R

class ProductDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val productName = intent.getStringExtra("PRODUCT_NAME")
        val productPrice = intent.getStringExtra("PRODUCT_PRICE")
        val productImageResId = intent.getIntExtra("PRODUCT_IMAGE", 0)

        val detailProductImageView: ImageView = findViewById(R.id.detailProductImageView)
        val detailProductNameTextView: TextView = findViewById(R.id.detailProductNameTextView)
        val detailProductPriceTextView: TextView = findViewById(R.id.detailProductPriceTextView)
        val otherDetailTextView: TextView = findViewById(R.id.otherDetailTextView)

        detailProductImageView.setImageResource(productImageResId)
        detailProductNameTextView.text = productName
        detailProductPriceTextView.text = productPrice
        otherDetailTextView.text = productName?.let { getOtherDetailText(it) }


        val backButton: ImageView = findViewById(R.id.icon_back)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getOtherDetailText(productName: String): String {
        // You can customize this method based on the product name
        return when (productName) {
            "Roti" -> "Other Detail for Roti: Your other detail text here"
            "Sayur" -> "Other Detail for Sayur: Your other detail text here"
            "Obat" -> "Other Detail for Obat: Your other detail text here"
            "Madu" -> "Other Detail for Madu: Your other detail text here"

            else -> "Other Detail: Your default other detail text here"
        }
    }
}

