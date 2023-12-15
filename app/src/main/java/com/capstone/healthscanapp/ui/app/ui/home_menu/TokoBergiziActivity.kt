package com.capstone.healthscanapp.ui.app.ui.home_menu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.healthscanapp.R
import com.capstone.healthscanapp.data.CartItem

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

        // Setup the cart ImageView click listener
        val cartImageView: ImageView = findViewById(R.id.cartImageView)
        cartImageView.setOnClickListener {
            // Start the CartActivity when the cart icon is clicked
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

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
        private val productPriceTextView: TextView =
            itemView.findViewById(R.id.productPriceTextView)
        private val buyButton: Button = itemView.findViewById(R.id.buyButton)

        fun bind(product: Product) {
            productImageView.setImageResource(product.imageResId)
            productNameTextView.text = product.name
            productPriceTextView.text = product.price

            buyButton.setOnClickListener {
                // Here you add the product to your cart (you'll need to implement this logic)
                addToCart(product)

                // Then navigate to CartActivity
                val intent = Intent(this@TokoBergiziActivity, CartActivity::class.java)
                startActivity(intent)
            }
        }

        // Dummy function to illustrate adding to cart
        private fun addToCart(product: Product) {
            val cartItem = CartItem(product.name, product.price)
            CartManager.addToCart(cartItem)

            // Optionally show a message to the user
            Toast.makeText(itemView.context, "Added to cart!", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.botton_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.cart -> {
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
