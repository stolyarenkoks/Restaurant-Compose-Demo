package com.sks.littlelemon.screens.cart

import androidx.lifecycle.ViewModel
import com.sks.littlelemon.models.CartItem
import com.sks.littlelemon.models.Dish
import com.sks.littlelemon.repository.CartRepository
import kotlinx.coroutines.flow.StateFlow

class CartViewModel: ViewModel() {
    val cartItems: StateFlow<List<CartItem>> = CartRepository.cartItems
    val totalPrice: Double get() = CartRepository.totalPrice

    fun addToCart(dish: Dish, quantity: Int) {
        CartRepository.addToCart(dish, quantity)
    }

    fun removeItem(dishId: Int) {
        CartRepository.removeFromCart(dishId)
    }

    fun updateQuantity(dishId: Int, newQuantity: Int) {
        CartRepository.updateQuantity(dishId, newQuantity)
    }

    fun clearCart() {
        CartRepository.clearCart()
    }
}