package com.erdees.farmdataexercise.coreUtils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp

object Util {

    fun Context.findActivity(): Activity? = when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }


    fun Dp.toPx(density: Density) = value * density.density

    fun Float.toDp(density: Density) = this / density.density
}