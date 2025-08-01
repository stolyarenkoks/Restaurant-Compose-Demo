package com.sks.theyellowtable.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.sks.theyellowtable.database.MenuItemRoom

@Serializable
data class MenuItem(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("price")
    val price: String
) {
    fun toMenuItemRoom() = MenuItemRoom(
        id,
        title,
        price.toDouble()
    )
}
