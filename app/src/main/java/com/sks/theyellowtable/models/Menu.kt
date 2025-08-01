package com.sks.theyellowtable.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Menu(
    @SerialName("menu")
    val menu: List<MenuItem>
)

