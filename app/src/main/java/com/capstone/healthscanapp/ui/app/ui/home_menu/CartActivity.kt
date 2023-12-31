package com.capstone.healthscanapp.ui.app.ui.home_menu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.healthscanapp.R
import com.capstone.healthscanapp.adapter.CartItemAdapter
import com.capstone.healthscanapp.adapter.OnItemClickListener
import com.capstone.healthscanapp.adapter.OnQuantityChangeListener
import com.capstone.healthscanapp.data.CartItem
import com.capstone.healthscanapp.data.CartManager
import com.capstone.healthscanapp.data.Product
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CartActivity : AppCompatActivity(), OnItemClickListener {
    private lateinit var cartItemsRecyclerView: RecyclerView
    private lateinit var cartEmptyTextView: TextView
    private lateinit var checkoutButton: Button
    private lateinit var totalTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        cartItemsRecyclerView = findViewById(R.id.cartItemsRecyclerView)
        checkoutButton = findViewById(R.id.checkoutButton)
        totalTextView = findViewById(R.id.totalTextView)
        cartEmptyTextView = findViewById(R.id.cartEmptyTextView)

        val cartItems = CartManager.getCartItems()

        if (cartItems.isEmpty()) {
            cartEmptyTextView.visibility = View.VISIBLE
        } else {
            cartEmptyTextView.visibility = View.GONE
            cartItemsRecyclerView.visibility = View.VISIBLE
            checkoutButton.visibility = View.VISIBLE

            cartItemsRecyclerView.layoutManager = LinearLayoutManager(this)

            val adapter = CartItemAdapter(cartItems, this, object : OnQuantityChangeListener {
                override fun onQuantityChanged() {
                    updateCartUI()
                }
            })

            cartItemsRecyclerView.adapter = adapter
        }

        checkoutButton.setOnClickListener {
            checkoutViaWhatsApp(cartItems)
        }

        val backButton: ImageView = findViewById(R.id.icon_back)
        backButton.setOnClickListener {
            onBackPressed()
        }

        updateCartUI()
    }

    private fun getCartItems(): List<CartItem> {
        // TODO: Implement your logic to retrieve cart items
        return emptyList()
    }

    override fun onItemClick(product: Product) {

        val cartItem = CartItem(
            name = product.name,
            price = product.price.replace("Rp ", "").replace(",", "").toDoubleOrNull() ?: 0.0,
            quantity = 1.0,  // Ensure quantity is of type Double
            imageResId = product.imageResId
        )
        CartManager.addToCart(cartItem)

        updateCartUI()
    }

    private fun updateCartUI() {
        val cartItems = CartManager.getCartItems()

        if (cartItems.isEmpty()) {
            cartEmptyTextView.visibility = View.VISIBLE
            cartItemsRecyclerView.visibility = View.GONE
            checkoutButton.visibility = View.GONE
            totalTextView.text = "Total: Rp 0.0"
        } else {
            cartEmptyTextView.visibility = View.GONE
            cartItemsRecyclerView.visibility = View.VISIBLE
            checkoutButton.visibility = View.VISIBLE

            cartItemsRecyclerView.layoutManager = LinearLayoutManager(this)

            val adapter = CartItemAdapter(cartItems, this, object : OnQuantityChangeListener {
                override fun onQuantityChanged() {
                    updateCartUI()
                }
            })

            cartItemsRecyclerView.adapter = adapter

            val myIndonesianLocale = Locale("in", "ID")
            val formatter: NumberFormat = NumberFormat.getCurrencyInstance(myIndonesianLocale)
            val totalPrice = formatter.format(calculateTotalPrice(cartItems))
            totalTextView.text = "Total: $totalPrice"
            Log.d("CartActivity", "Is totalTextView visible? ${totalTextView.visibility == View.VISIBLE}")
        }

        cartItemsRecyclerView.adapter?.notifyDataSetChanged()
    }


    private fun calculateTotalPrice(cartItems: List<CartItem>): Double {

        var totalPrice = 0.0

        for (cartItem in cartItems) {
            totalPrice += cartItem.price * cartItem.quantity
        }

        return totalPrice
    }

    private fun checkoutViaWhatsApp(cartItems: List<CartItem>) {
        val message = buildWhatsAppMessage(cartItems)

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.`package` = "com.whatsapp"
        intent.putExtra(Intent.EXTRA_TEXT, message)

        try {
            startActivity(intent)
        } catch (e: Exception) {
            // Handle the case where WhatsApp is not installed
            // You may want to inform the user to install WhatsApp
        }
    }

    private fun buildWhatsAppMessage(cartItems: List<CartItem>): String {
        val builder = StringBuilder()
        builder.append("Hai, Saya ingin memesan:\n")

        for (cartItem in cartItems) {
            builder.append("${cartItem.name} x ${cartItem.quantity}\n")
        }

        val totalPrice = calculateTotalPrice(cartItems)
        builder.append("Total Harga: Rp $totalPrice\n")


        builder.append("Nama: Rijal\n")
        builder.append("Alamat: Bandung, Jl.Heaven 123\n")
        builder.append("Tanggal: ${getCurrentDate()}\n")

        return builder.toString()
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = Date()
        return dateFormat.format(currentDate)
    }
}
