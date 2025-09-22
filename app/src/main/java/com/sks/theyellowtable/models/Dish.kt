package com.sks.theyellowtable.models

import com.sks.theyellowtable.R

data class Dish(
    val id: Int,
    val nameResourceId: Int,
    val descriptionResourceId: Int,
    val price: Double,
    val imageResource: Int
) {
    companion object
}

fun Dish.Companion.mock(
    id: Int = 1,
    nameResourceId: Int = R.string.greek_salad,
    descriptionResourceId: Int = R.string.greek_salad_description,
    price: Double = 12.99,
    imageResource: Int = R.drawable.greeksalad
): Dish {
    return Dish(
        id = id,
        nameResourceId = nameResourceId,
        descriptionResourceId = descriptionResourceId,
        price = price,
        imageResource = imageResource
    )
}