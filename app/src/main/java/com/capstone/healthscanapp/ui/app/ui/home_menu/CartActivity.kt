package com.capstone.healthscanapp.ui.app.ui.home_menu

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.healthscanapp.R
import com.capstone.healthscanapp.adapter.CartItemAdapter
import com.capstone.healthscanapp.data.CartItem

class CartActivity : AppCompatActivity() {
    private lateinit var cartItemsRecyclerView: RecyclerView
    private lateinit var cartEmptyTextView: TextView
    private lateinit var checkoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        cartItemsRecyclerView = findViewById(R.id.cartItemsRecyclerView)
        checkoutButton = findViewById(R.id.checkoutButton)

        val cartItems = CartManager.getCartItems()

        if (cartItems.isEmpty()) {
            cartEmptyTextView.visibility = View.VISIBLE
        } else {
            cartItemsRecyclerView.visibility = View.VISIBLE
            checkoutButton.visibility = View.VISIBLE

            cartItemsRecyclerView.layoutManager = LinearLayoutManager(this)
            cartItemsRecyclerView.adapter = CartItemAdapter(cartItems)
        }
    }

    // Dummy function to illustrate retrieving cart items
    private fun getCartItems(): List<CartItem> {
        // TODO: Implement your logic to retrieve cart items
        return emptyList()
    }

}
