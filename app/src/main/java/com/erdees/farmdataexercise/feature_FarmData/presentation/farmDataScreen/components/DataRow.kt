package com.erdees.farmdataexercise.feature_FarmData.presentation.farmDataScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.erdees.farmdataexercise.feature_FarmData.domain.model.FarmData
import com.erdees.farmdataexercise.feature_FarmData.domain.model.Temperature
import com.erdees.farmdataexercise.feature_FarmData.domain.util.Format
import com.erdees.farmdataexercise.ui.theme.BackgroundColor

@Composable
fun DataRow(farmData: FarmData) {
    val farmDataValue =
        if (farmData.sensorType == Temperature().firebaseName) Format.formatTemperature(farmData.value) else farmData.value
    Row(Modifier.background(BackgroundColor)) {
        TableCell(text = Format.formatISO8601String(farmData.datetime), weight = 0.7f)
        TableCell(text = farmDataValue, weight = 0.3f)
    }
}