package com.sks.theyellowtable.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = TheYellowTableColor.yellow,
    secondary = TheYellowTableColor.pink
)

private val LightColorScheme = lightColorScheme(
    primary = TheYellowTableColor.yellow,
    secondary = TheYellowTableColor.pink,
    background = TheYellowTableColor.lightGray,
    surface = TheYellowTableColor.lightGray,
    surfaceContainer = TheYellowTableColor.lightGray,
    surfaceContainerLow = TheYellowTableColor.lightGray,
    surfaceContainerLowest = TheYellowTableColor.lightGray,
    surfaceContainerHigh = TheYellowTableColor.lightGray,
    surfaceContainerHighest = TheYellowTableColor.lightGray,
    primaryContainer = TheYellowTableColor.lightGray,
    tertiaryContainer = TheYellowTableColor.lightGray
)

@Composable
fun TheYellowTableTheme(
    darkTheme: Boolean = false, // Force light theme always
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Disable dynamic colors - use our custom colors
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}