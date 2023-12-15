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
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.healthscanapp.R
import com.capstone.healthscanapp.data.CartItem

class TokoBergiziActivity : AppCompatActivity() {

    private lateinit var productsRecyclerView: RecyclerView
    private lateinit var searchView: androidx.appcompat.widget.SearchView
    private lateinit var adapter: ProductAdapter

    private val allProducts = createDummyProductList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toko_bergizi)

        productsRecyclerView = findViewById(R.id.productsRecyclerView)
        searchView = findViewById(R.id.searchView)

        val layoutManager = GridLayoutManager(this, 2)
        productsRecyclerView.layoutManager = layoutManager

        adapter = ProductAdapter(allProducts)
        productsRecyclerView.adapter = adapter

        val cartImageView: ImageView = findViewById(R.id.cartImageView)
        cartImageView.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        setupSearchView()
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterProducts(newText)
                return true
            }
        })
    }

    private fun filterProducts(query: String?) {
        if (query.isNullOrBlank()) {
            adapter.updateData(allProducts)
        } else {
            val filteredProducts = allProducts.filter {
                it.name.contains(query, ignoreCase = true)
            }
            adapter.updateData(filteredProducts)
        }
    }

    private fun createDummyProductList(): List<Product> {
        return listOf(
            Product("Roti", "Rp 100,000", R.drawable.roti),
            Product("Sayur", "Rp 200,000", R.drawable.sayur),
            Product("Obat", "Rp 300,000", R.drawable.obat),
            Product("Madu", "Rp 400,000", R.drawable.madu),
            // Add more products as needed
        )
    }

    data class Product(val name: String, val price: String, val imageResId: Int)

    inner class ProductAdapter(private var productList: List<Product>) :
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

        fun updateData(newList: List<Product>) {
            productList = newList
            notifyDataSetChanged()
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
                // Add the product to the cart
                addToCart(product)

                // Notify the user
                Toast.makeText(itemView.context, "Added to cart!", Toast.LENGTH_SHORT).show()
            }
        }

        private fun addToCart(product: Product) {
            val cartItem = CartItem(product.name, product.price, product.imageResId)
            CartManager.addToCart(cartItem)
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