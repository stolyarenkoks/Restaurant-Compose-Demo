package com.sks.littlelemon.models

import com.sks.littlelemon.R

data class Dish(
    val id: Int,
    val nameResourceId: Int,
    val descriptionResourceId: Int,
    val price: Double,
    val imageResource: Int
) {
    companion object
}

fun Dish.Companion.mock(): Dish {
    return Dish(
        id = 1,
        nameResourceId = R.string.greek_salad,
        descriptionResourceId = R.string.greek_salad_description,
        price = 12.99,
        imageResource = R.drawable.greeksalad
    )
}