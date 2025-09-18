package com.sks.theyellowtable.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MenuItemEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val price: Double,
)