package com.capstone.healthscanapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.healthscanapp.R
import com.capstone.healthscanapp.data.CartItem
import java.text.NumberFormat
import java.util.Locale

interface OnQuantityChangeListener {
    fun onQuantityChanged()
}
class CartItemAdapter(
    private val cartItems: List<CartItem>,
    private val itemClickListener: OnItemClickListener,
    private val quantityChangeListener: OnQuantityChangeListener
) : RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartItemViewHolder(itemView, this)
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.bind(cartItem, itemClickListener)
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    class CartItemViewHolder(itemView: View, private val adapter: CartItemAdapter) :
        RecyclerView.ViewHolder(itemView) {
        private val itemNameTextView: TextView = itemView.findViewById(R.id.itemNameTextView)
        private val itemPriceTextView: TextView = itemView.findViewById(R.id.itemPriceTextView)
        private val itemImageView: ImageView = itemView.findViewById(R.id.itemImageView)
        private val quantityTextView: TextView = itemView.findViewById(R.id.quantityTextView)
        private val decrementButton: Button = itemView.findViewById(R.id.decrementButton)
        private val incrementButton: Button = itemView.findViewById(R.id.incrementButton)

        fun bind(cartItem: CartItem, itemClickListener: OnItemClickListener) {
            val myIndonesianLocale = Locale("in", "ID")
            val formatter: NumberFormat = NumberFormat.getCurrencyInstance(myIndonesianLocale)
            val price = formatter.format(cartItem.price * cartItem.quantity)

            itemNameTextView.text = cartItem.name
            itemPriceTextView.text = price
            quantityTextView.text = cartItem.quantity.toInt().toString()

            itemImageView.setImageResource(cartItem.imageResId)

            decrementButton.setOnClickListener {
                if (cartItem.quantity > 1) {
                    cartItem.quantity--
                    adapter.notifyItemChanged(adapterPosition)
                    adapter.quantityChangeListener.onQuantityChanged()
                }
            }

            incrementButton.setOnClickListener {
                cartItem.quantity++
                adapter.notifyItemChanged(adapterPosition)
                adapter.quantityChangeListener.onQuantityChanged()
            }
        }
    }
}
