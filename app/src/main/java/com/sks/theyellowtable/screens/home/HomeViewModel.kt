package com.sks.theyellowtable.screens.home

import androidx.lifecycle.ViewModel
import com.sks.theyellowtable.models.Dish
import com.sks.theyellowtable.repository.DishRepository

class HomeViewModel: ViewModel() {
    val dishes: List<Dish> = DishRepository.dishes
}