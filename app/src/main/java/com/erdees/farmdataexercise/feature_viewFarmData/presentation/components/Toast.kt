package com.erdees.farmdataexercise.feature_viewFarmData.presentation.components

import android.widget.Toast.LENGTH_SHORT
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun Toast(message: String?) {
    android.widget.Toast.makeText(LocalContext.current, message, LENGTH_SHORT).show()
}