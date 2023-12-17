package com.capstone.healthscanapp.data

data class CartItem(
    val name: String,
    val price: Double,
    val imageResId: Int,
    var quantity: Double = 1.0
)

