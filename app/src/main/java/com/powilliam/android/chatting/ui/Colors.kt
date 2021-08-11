package com.powilliam.android.chatting.ui

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

private val DarkBackground = Color(0xFF1C1C1E)
private val DarkSurfaceColor = Color(0xFF2C2C2E)

private val Primary = Color(0xFFF2C94C)
private val OnPrimary = Color(0xFF000000)

val LightColors = lightColors(primary = Primary, onPrimary = OnPrimary)
val DarkColors = darkColors(
    primary = Primary,
    onPrimary = OnPrimary,
    background = DarkBackground,
    surface = DarkSurfaceColor
)