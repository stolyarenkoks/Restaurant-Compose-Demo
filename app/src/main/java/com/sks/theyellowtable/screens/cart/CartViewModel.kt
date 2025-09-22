package com.sks.theyellowtable.screens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sks.theyellowtable.models.CartItem
import com.sks.theyellowtable.models.Dish
import com.sks.theyellowtable.repository.CartRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CartViewModel(
    val cartRepository: CartRepository
): ViewModel() {
    val cartItems: StateFlow<List<CartItem>> = cartRepository.cartItems
    val totalPrice: Double get() = cartRepository.totalPrice

    fun addToCart(dish: Dish, quantity: Int) {
        viewModelScope.launch {
            cartRepository.addToCart(dish, quantity)
        }
    }

    fun removeItem(dishId: Int) {
        viewModelScope.launch {
            cartRepository.removeFromCart(dishId)
        }
    }

    fun updateQuantity(dishId: Int, newQuantity: Int) {
        viewModelScope.launch {
            cartRepository.updateQuantity(dishId, newQuantity)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            cartRepository.clearCart()
        }
    }
}