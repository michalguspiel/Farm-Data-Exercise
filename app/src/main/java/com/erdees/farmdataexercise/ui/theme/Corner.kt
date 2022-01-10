package com.erdees.farmdataexercise.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Corner(
    val default : Dp = 8.dp,
    val small : Dp = 4.dp,
    val medium : Dp = 8.dp,
    val large : Dp = 12.dp,
    val xLarge : Dp = 18.dp,
)
val LocalCorner = compositionLocalOf { Corner() }