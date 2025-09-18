package com.sks.theyellowtable.models

import com.sks.theyellowtable.database.model.MenuItemEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MenuItem(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("price")
    val price: String
) {
    fun toMenuItemRoom() = MenuItemEntity(
        id,
        title,
        price.toDouble()
    )
}
