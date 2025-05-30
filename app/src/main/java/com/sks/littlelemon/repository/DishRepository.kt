package com.sks.littlelemon.repository

import com.sks.littlelemon.R
import com.sks.littlelemon.models.Dish

object DishRepository {
    val dishes = listOf(
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

    fun getDish(id: Int) = dishes.firstOrNull { it.id == id }
}
