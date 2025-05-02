package com.sks.littlelemon.screens.dishDetails

import androidx.lifecycle.ViewModel
import com.sks.littlelemon.models.Dish
import com.sks.littlelemon.repository.DishRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class DishDetailsUIState(
    val dish: Dish? = null,
    val quantity: Int = 1
)

class DishDetailsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DishDetailsUIState())
    val uiState: StateFlow<DishDetailsUIState> = _uiState.asStateFlow()

    fun loadDish(dishId: Int) {
        val dish = DishRepository.getDish(dishId)
        _uiState.update { it.copy(dish = dish) }
    }

    fun updateQuantity(newQuantity: Int) {
        _uiState.update { it.copy(quantity = newQuantity) }
    }

    fun addToCart() {}
} 