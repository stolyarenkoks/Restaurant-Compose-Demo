package com.sks.theyellowtable.models

data class CartItem(
    val dish: Dish,
    val quantity: Int
) {
    val totalPrice: Double get() = dish.price * quantity
} 