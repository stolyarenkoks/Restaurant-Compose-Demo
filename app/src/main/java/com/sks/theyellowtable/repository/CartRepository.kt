package com.sks.theyellowtable.repository

import com.sks.theyellowtable.models.CartItem
import com.sks.theyellowtable.models.Dish
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object CartRepository {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    val totalPrice: Double
        get() = _cartItems.value.sumOf { it.totalPrice }

    fun addToCart(dish: Dish, quantity: Int) {
        _cartItems.update { currentItems ->
            val existingItem = currentItems.find { it.dish.id == dish.id }
            if (existingItem != null) {
                currentItems.map { 
                    if (it.dish.id == dish.id) {
                        it.copy(quantity = it.quantity + quantity)
                    } else {
                        it
                    }
                }
            } else {
                currentItems + CartItem(dish, quantity)
            }
        }
    }

    fun removeFromCart(dishId: Int) {
        _cartItems.update { it.filter { item -> item.dish.id != dishId } }
    }

    fun updateQuantity(dishId: Int, newQuantity: Int) {
        _cartItems.update { currentItems ->
            currentItems.map { 
                if (it.dish.id == dishId) {
                    it.copy(quantity = newQuantity)
                } else {
                    it
                }
            }
        }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }
} 