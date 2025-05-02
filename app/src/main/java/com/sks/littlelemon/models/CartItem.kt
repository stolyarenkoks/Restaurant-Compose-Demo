package com.sks.littlelemon.models

data class CartItem(
    val dish: Dish,
    val quantity: Int
) {
    val totalPrice: Double
        get() = dish.price * quantity
} 