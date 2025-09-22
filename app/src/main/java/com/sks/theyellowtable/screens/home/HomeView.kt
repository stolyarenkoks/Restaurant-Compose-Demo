package com.sks.theyellowtable.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.koin.androidx.compose.koinViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sks.theyellowtable.destinations.Cart
import com.sks.theyellowtable.destinations.DishDetails
import com.sks.theyellowtable.destinations.Menu
import com.sks.theyellowtable.models.Dish
import com.sks.theyellowtable.models.mock
import com.sks.theyellowtable.repository.DishRepository
import com.sks.theyellowtable.repository.DishRepositoryImpl
import com.sks.theyellowtable.views.TopBar

// MARK: - View

@Composable
fun HomeView(
    navController: NavHostController,
    viewModel: HomeViewModel = koinViewModel()
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

        HomeHeaderView(onMenuTapped = {
            navController.navigate(Menu.route)
        })

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
    val mockDishes = listOf(
        Dish.mock(),
        Dish(
            2,
            com.sks.theyellowtable.R.string.bruschetta,
            com.sks.theyellowtable.R.string.bruschetta_description,
            8.50,
            com.sks.theyellowtable.R.drawable.bruschetta
        )
    )
    HomeView(
        navController = rememberNavController(),
        dishes = mockDishes
    )
}