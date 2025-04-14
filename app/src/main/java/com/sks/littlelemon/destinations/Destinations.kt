package com.sks.littlelemon.destinations

interface Destinations {
    val route: String
}

object Home : Destinations {
    override val route = "Home"
}

object Menu : Destinations {
    override val route = "Menu"
}

object DishDetails : Destinations {
    override val route = "DishDetails"
    const val dishId = "dish_id"
}