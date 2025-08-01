package com.sks.theyellowtable.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sks.theyellowtable.ui.theme.TheYellowTableColor

val Typography = Typography(
    headlineLarge = TextStyle(
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold,
        color = TheYellowTableColor.charcoal
    ),
    headlineMedium = TextStyle(
        color = TheYellowTableColor.charcoal,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    ),
    bodyLarge = TextStyle(
        color = TheYellowTableColor.green
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        color = TheYellowTableColor.green
    )
)