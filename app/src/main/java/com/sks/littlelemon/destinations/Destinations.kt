package com.sks.littlelemon.destinations

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

abstract class Destinations {
    abstract val route: String
    open val icon: ImageVector? = null
    open val title: String? = null
}

object Home: Destinations() {
    override val route = "Home"
    override val icon = Icons.Filled.Home
    override val title = "Home"
}

object Profile: Destinations() {
    override val route = "Profile"
    override val icon = Icons.Filled.Person
    override val title = "Profile"
}

object DishDetails: Destinations() {
    override val route = "DishDetails"
    const val dishId = "dish_id"
}

object Cart: Destinations() {
    override val route = "cart"
    override val icon = Icons.Filled.ShoppingCart
    override val title = "Cart"
}