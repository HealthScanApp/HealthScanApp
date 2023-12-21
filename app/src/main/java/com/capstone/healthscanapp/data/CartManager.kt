package com.capstone.healthscanapp.data

object CartManager {
    private val cartItems = mutableListOf<CartItem>()

    fun addToCart(item: CartItem) {
        cartItems.add(item)
    }

    fun getCartItem(productName: String): CartItem? {
        return cartItems.find { it.name == productName }
    }

    fun getCartItems(): List<CartItem> {
        return cartItems
    }

    fun clearCart() {
        cartItems.clear()
    }
}