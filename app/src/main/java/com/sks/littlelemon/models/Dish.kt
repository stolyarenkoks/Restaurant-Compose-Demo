package com.sks.littlelemon.models

data class Dish(
    val id: Int,
    val nameResourceId: Int,
    val descriptionResourceId: Int,
    val price: Double,
    val imageResource: Int
)