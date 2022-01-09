package com.erdees.farmdataexercise.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ), h1 = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 96.sp,
        letterSpacing = (-1.5).sp
    ),
    h2 = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 60.sp,
        letterSpacing = (-0.5).sp
    ), h3 = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 48.sp,
        letterSpacing = (-0.25).sp
    ),h4 = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 36.sp,
        letterSpacing = (-0.10).sp
    ),h5 = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 30.sp,
        letterSpacing = (-0.05).sp
    )
    ,button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W600,
        fontSize = 16.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        color = OnPrimaryLightest
    )
/* Other default text styles to override
button = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.W500,
    fontSize = 14.sp
),
caption = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp
)
*/
)