package com.capstone.healthscanapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.healthscanapp.R
import com.capstone.healthscanapp.data.CartItem
import java.text.NumberFormat
import java.util.Locale

class CartItemAdapter(
    private val cartItems: List<CartItem>,
    private val itemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.bind(cartItem)
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    // ViewHolder class for each item in the cart
    class CartItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemNameTextView: TextView = itemView.findViewById(R.id.itemNameTextView)
        private val itemPriceTextView: TextView = itemView.findViewById(R.id.itemPriceTextView)
        private val itemImageView: ImageView = itemView.findViewById(R.id.itemImageView)

        fun bind(cartItem: CartItem) {
            val myIndonesianLocale = Locale("in", "ID")
            val formater: NumberFormat = NumberFormat.getCurrencyInstance(myIndonesianLocale)
            val price = formater.format(cartItem.price)
            itemNameTextView.text = cartItem.name
            itemPriceTextView.text = price
            itemImageView.setImageResource(cartItem.imageResId)
        }
    }

}
