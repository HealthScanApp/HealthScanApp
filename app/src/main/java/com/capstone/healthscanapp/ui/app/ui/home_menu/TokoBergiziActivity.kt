package com.capstone.healthscanapp.ui.app.ui.home_menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.healthscanapp.R

class TokoBergiziActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toko_bergizi)

        val productsRecyclerView: RecyclerView = findViewById(R.id.productsRecyclerView)
        val layoutManager = GridLayoutManager(this, 2) // Adjust the spanCount as needed
        productsRecyclerView.layoutManager = layoutManager

        val products = createDummyProductList() // Replace with your actual product data

        val adapter = ProductAdapter(products)
        productsRecyclerView.adapter = adapter
    }

    private fun createDummyProductList(): List<Product> {
        // Replace this with your actual product data
        return listOf(
            Product("Roti", "Rp 100,000", R.drawable.roti),
            // Add more products as needed
        )
    }

    data class Product(val name: String, val price: String, val imageResId: Int)

    inner class ProductAdapter(private val productList: List<Product>) :
        RecyclerView.Adapter<ProductViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product, parent, false)
            return ProductViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
            val product = productList[position]
            holder.bind(product)
        }

        override fun getItemCount(): Int {
            return productList.size
        }
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productImageView: ImageView = itemView.findViewById(R.id.productImageView)
        private val productNameTextView: TextView = itemView.findViewById(R.id.productNameTextView)
        private val productPriceTextView: TextView = itemView.findViewById(R.id.productPriceTextView)
        private val buyButton: Button = itemView.findViewById(R.id.buyButton)

        fun bind(product: Product) {
            productImageView.setImageResource(product.imageResId)
            productNameTextView.text = product.name
            productPriceTextView.text = product.price

            // Set onClickListener for the buy button if needed
            buyButton.setOnClickListener {
                // Handle the buy button click
            }
        }
    }
}
