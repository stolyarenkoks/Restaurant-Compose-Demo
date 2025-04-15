package com.sks.littlelemon.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.sks.littlelemon.repository.DishRepository
import com.sks.littlelemon.screens.navigation.TopBar

@Composable
fun HomeScreen(navController: NavHostController) {
    HomeView(navController = navController)
}

@Composable
private fun HomeView(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar()
        HomeHeaderView()
        HomeDishesView(
            navController = navController,
            dishes = DishRepository.dishes
        )
    }
}