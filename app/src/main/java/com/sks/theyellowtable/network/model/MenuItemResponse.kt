package com.sks.theyellowtable.network.model

import com.sks.theyellowtable.database.model.MenuItemEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenuItemResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("price")
    val price: String
) {
    fun toMenuItemEntity() = MenuItemEntity(
        id,
        title,
        price.toDouble()
    )
}