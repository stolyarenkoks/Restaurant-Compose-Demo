package com.sks.littlelemon.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.sks.littlelemon.repository.DishRepository
import com.sks.littlelemon.screens.navigation.TopBar

@Composable
fun HomeScreen(navController: NavHostController, onUserSignedOut: () -> Unit) {
    HomeView(navController = navController, onUserSignedOut = onUserSignedOut)
}

@Composable
private fun HomeView(navController: NavHostController, onUserSignedOut: () -> Unit = {}) {
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