package com.capstone.healthscanapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.healthscanapp.R
import com.capstone.healthscanapp.data.CartItem

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
            itemNameTextView.text = cartItem.name
            itemPriceTextView.text = cartItem.price.toString()
            itemImageView.setImageResource(cartItem.imageResId)
        }
    }

}
