package com.sks.theyellowtable.screens.dishDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sks.theyellowtable.models.Dish
import com.sks.theyellowtable.repository.CartRepository
import com.sks.theyellowtable.repository.DishRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

data class DishDetailsUIState(
    val dish: Dish? = null,
    val quantity: Int = 1
)

class DishDetailsViewModel(
    val cartRepository: CartRepository,
    val dishRepository: DishRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(DishDetailsUIState())
    val uiState: StateFlow<DishDetailsUIState> = _uiState.asStateFlow()

    fun loadDish(dishId: Int) {
        viewModelScope.launch {
            val dish = dishRepository.getDish(dishId)
            _uiState.update { it.copy(dish = dish) }
        }
    }

    fun updateQuantity(newQuantity: Int) {
        _uiState.update { it.copy(quantity = newQuantity) }
    }

    fun addToCart() {
        _uiState.value.dish?.let { dish ->
            viewModelScope.launch {
                cartRepository.addToCart(dish, _uiState.value.quantity)
            }
        }
    }
} 