package com.powilliam.android.chatting.ui

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

private val DarkBackground = Color(0xFF1C1C1E)
private val DarkSurfaceColor = Color(0xFF2C2C2E)

private val DeepPurpleA200 = Color(0xFF7C4DFF)
private val YellowA400 = Color(0xFFFFEA00)

val LightColors = lightColors(primary = DeepPurpleA200, onPrimary = Color.White)
val DarkColors = darkColors(
    primary = YellowA400,
    onPrimary = Color.Black,
    background = DarkBackground,
    surface = DarkSurfaceColor
)