package com.sks.theyellowtable.repository

import com.sks.theyellowtable.models.CartItem
import com.sks.theyellowtable.models.Dish
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.collections.plus

interface CartRepository {
    val cartItems: StateFlow<List<CartItem>>
    val totalPrice: Double

    suspend fun addToCart(dish: Dish, quantity: Int)
    suspend fun removeFromCart(dishId: Int)
    suspend fun updateQuantity(dishId: Int, newQuantity: Int)
    suspend fun clearCart()
}

class CartRepositoryImpl: CartRepository {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    override val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    override val totalPrice: Double
        get() = _cartItems.value.sumOf { it.totalPrice }

    override suspend fun addToCart(dish: Dish, quantity: Int) {
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

    override suspend fun removeFromCart(dishId: Int) {
        _cartItems.update { it.filter { item -> item.dish.id != dishId } }
    }

    override suspend fun updateQuantity(dishId: Int, newQuantity: Int) {
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

    override suspend fun clearCart() {
        _cartItems.value = emptyList()
    }
}