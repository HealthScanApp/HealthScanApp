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
import com.capstone.healthscanapp.ui.app.ui.home_menu.TokoBergiziActivity

interface OnItemClickListener {
    fun onItemClick(product: TokoBergiziActivity.Product)
}

class ProductAdapter(
    private var productList: List<TokoBergiziActivity.Product>,
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
        holder.itemView.setOnClickListener { itemClickListener.onItemClick(product) }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun updateData(newList: List<TokoBergiziActivity.Product>) {
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

        fun bind(product: TokoBergiziActivity.Product) {
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

        private fun addToCart(product: TokoBergiziActivity.Product) {
            val cartItem = CartItem(product.name, product.price, product.imageResId)
            // Assuming you have a CartManager somewhere to handle cart operations
            CartManager.addToCart(cartItem)
        }
    }
}
