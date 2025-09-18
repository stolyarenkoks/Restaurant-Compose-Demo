package com.sks.theyellowtable.repository

import com.sks.theyellowtable.R
import com.sks.theyellowtable.models.CartItem
import com.sks.theyellowtable.models.Dish
import kotlinx.coroutines.flow.StateFlow

interface DishRepository {
    val dishes: List<Dish>

    suspend fun getDish(id: Int): Dish?
}

class DishRepositoryImpl: DishRepository {
    override val dishes = listOf(
        Dish(
            id = 1,
            nameResourceId = R.string.greek_salad,
            descriptionResourceId = R.string.greek_salad_description,
            price = 12.99,
            imageResource = R.drawable.greeksalad
        ),
        Dish(
            id = 2,
            nameResourceId = R.string.lemon_dessert,
            descriptionResourceId = R.string.lemon_dessert_description,
            price = 5.00,
            imageResource = R.drawable.lemondessert
        ),
        Dish(
            id = 3,
            nameResourceId = R.string.bruschetta,
            descriptionResourceId = R.string.bruschetta_description,
            price = 7.99,
            imageResource = R.drawable.bruschetta
        ),
        Dish(
            id = 4,
            nameResourceId = R.string.grilled_fish,
            descriptionResourceId = R.string.grilled_fish_description,
            price = 20.00,
            imageResource = R.drawable.grilledfish
        ),
        Dish(
            id = 5,
            nameResourceId = R.string.pasta,
            descriptionResourceId = R.string.pasta_description,
            price = 18.99,
            imageResource = R.drawable.pasta
        ),
        Dish(
            id = 6,
            nameResourceId = R.string.lasagne,
            descriptionResourceId = R.string.lasagne_description,
            price = 22.99,
            imageResource = R.drawable.lasagne
        )
    )

    override suspend fun getDish(id: Int): Dish? {
        return dishes.firstOrNull { it.id == id }
    }
}
