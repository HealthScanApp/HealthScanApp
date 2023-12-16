package com.capstone.healthscanapp.ui.app.ui.home_menu

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.healthscanapp.R
import com.capstone.healthscanapp.adapter.OnItemClickListener
import com.capstone.healthscanapp.adapter.ProductAdapter

class TokoBergiziActivity : AppCompatActivity(), OnItemClickListener {

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

        adapter = ProductAdapter(allProducts, this)
        productsRecyclerView.adapter = adapter

        val cartImageView: ImageView = findViewById(R.id.cartImageView)
        cartImageView.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        setupSearchView()
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
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

    override fun onItemClick(product: Product) {
        // Handle item click, e.g., show detail view
        showProductDetail(product)
    }

    private fun showProductDetail(product: Product) {
        val detailIntent = Intent(this, ProductDetailActivity::class.java)
        detailIntent.putExtra("PRODUCT_NAME", product.name)
        detailIntent.putExtra("PRODUCT_PRICE", product.price)
        detailIntent.putExtra("PRODUCT_IMAGE", product.imageResId)
        // Add other details as needed
        startActivity(detailIntent)
    }
}