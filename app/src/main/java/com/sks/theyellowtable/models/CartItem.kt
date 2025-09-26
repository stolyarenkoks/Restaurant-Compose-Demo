package com.sks.theyellowtable.models

data class CartItem(
    val dish: Dish,
    val quantity: Int
) {
    val totalPrice: Double get() = dish.price * quantity

    companion object
}

fun CartItem.Companion.mock(
    dish: Dish = Dish.mock(),
    quantity: Int = 1
): CartItem {
    return CartItem(
        dish = dish,
        quantity = quantity
    )
}