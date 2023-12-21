package com.capstone.healthscanapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.capstone.healthscanapp.R
import com.capstone.healthscanapp.data.CartItem
import com.capstone.healthscanapp.data.CartManager
import com.capstone.healthscanapp.data.Product
import com.capstone.healthscanapp.ui.app.ui.home_menu.TokoBergiziActivity

interface OnItemClickListener {
    fun onItemClick(product: Product)
}

class ProductAdapter(
    private var productList: List<Product>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

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

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productImageView: ImageView = itemView.findViewById(R.id.productImageView)
        private val productNameTextView: TextView =
            itemView.findViewById(R.id.productNameTextView)
        private val productPriceTextView: TextView =
            itemView.findViewById(R.id.productPriceTextView)
        private val buyButton: Button = itemView.findViewById(R.id.buyButton)

        fun bind(product: Product) {
            productImageView.setImageResource(product.imageResId)
            productNameTextView.text = product.name
            productPriceTextView.text = product.price

            productImageView.setOnClickListener {
                itemClickListener.onItemClick(product)
            }
            buyButton.setOnClickListener {

                addToCart(product)

                Toast.makeText(itemView.context, "Added to cart!", Toast.LENGTH_SHORT).show()
            }
        }

        private fun addToCart(product: Product) {
            val existingCartItem = CartManager.getCartItem(product.name)

            val priceAsDouble =
                product.price.replace("Rp ", "").replace(",", "").toDoubleOrNull() ?: 0.0

            if (existingCartItem != null) {
                existingCartItem.quantity++
            } else {
                val cartItem = CartItem(product.name, priceAsDouble, product.imageResId, 1.0)
                CartManager.addToCart(cartItem)
            }

        }

    }
}
