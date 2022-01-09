package com.erdees.farmdataexercise.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = Yellow300,
    primaryVariant = Yellow400,
    secondary = Green400,
    onPrimary = OnPrimaryLight
)

private val LightColorPalette = lightColors(
    primary = Yellow100,
    primaryVariant = Yellow200,
    secondary = Green500,
    onPrimary = OnPrimary

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun FarmDataExerciseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    val colors = if (darkTheme) {
        systemUiController.setSystemBarsColor(
            color = OnPrimaryLight
        )
        DarkColorPalette
    } else {
        systemUiController.setSystemBarsColor(
            color = OnPrimaryLight
        )
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}