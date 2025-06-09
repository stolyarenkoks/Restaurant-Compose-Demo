package com.sks.littlelemon.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sks.littlelemon.destinations.Cart
import com.sks.littlelemon.destinations.DishDetails
import com.sks.littlelemon.models.Dish
import com.sks.littlelemon.views.TopBar

// MARK: - View

@Composable
fun HomeView(
    navController: NavHostController,
    viewModel: HomeViewModel = viewModel()
) {
    HomeView(
        navController = navController,
        dishes = viewModel.dishes
    )
}

// MARK: - Private View Components

@Composable
private fun HomeView(
    navController: NavHostController,
    dishes: List<Dish> = listOf()
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            showBackButton = false,
            showCartIcon = true,
            onBackClick = { navController.navigateUp() },
            onCartClick = { navController.navigate(Cart.route) }
        )

        HomeHeaderView()

        HomeDishesView(
            dishes = dishes,
            onDishSelected = { id ->
                navController.navigate("${DishDetails.route}/${id}")
            }
        )
    }
}

// MARK: - Preview

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomePreview() {
    val viewModel: HomeViewModel = viewModel()
    HomeView(
        navController = rememberNavController(),
        viewModel = viewModel
    )
}