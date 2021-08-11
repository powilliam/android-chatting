package com.powilliam.android.chatting.ui

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.powilliam.android.chatting.R

private val sourceSans = FontFamily(
    listOf(
        Font(resId = R.font.sourcesanspro_regular, weight = FontWeight.Normal),
        Font(resId = R.font.sourcesanspro_semibold, weight = FontWeight.SemiBold),
        Font(resId = R.font.sourcesanspro_bold, weight = FontWeight.Bold),
    )
)

val typography = Typography(
    defaultFontFamily = sourceSans
)