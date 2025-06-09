package com.sks.littlelemon.screens.home

import androidx.lifecycle.ViewModel
import com.sks.littlelemon.models.Dish
import com.sks.littlelemon.repository.DishRepository

class HomeViewModel: ViewModel() {
    val dishes: List<Dish> = DishRepository.dishes
}