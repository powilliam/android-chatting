package com.powilliam.android.chatting.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun ChattingTheme(isSystemDark: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (!isSystemDark) {
        LightColors
    } else {
        DarkColors
    }

    MaterialTheme(colors = colors, typography = typography, shapes = shapes, content = content)
}