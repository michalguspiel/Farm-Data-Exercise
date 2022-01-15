package com.erdees.farmdataexercise.feature_FarmData.presentation.farmDataScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.erdees.farmdataexercise.feature_FarmData.domain.util.Format
import com.erdees.farmdataexercise.ui.theme.BackgroundColor
import com.erdees.farmdataexercise.ui.theme.LocalElevation
import com.erdees.farmdataexercise.ui.theme.OnPrimary
import com.erdees.farmdataexercise.ui.theme.Typography

@Composable
fun FarmDataInformationCard(
    locationName: String,
    sensorType: String,
    timeRange: String,
    timeRange2: String
) {
    Card(
        shape = RectangleShape,
        modifier = Modifier.background(BackgroundColor),
        elevation = LocalElevation.current.default
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = locationName,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                style = Typography.h4
            )
            Text(
                text = sensorType,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                style = Typography.h5
            )
            Divider(color = OnPrimary, thickness = 1.dp)
            Text(
                text = "Time range: ${Format.formatDateToYearMonthDay(timeRange)} - ${
                    Format.formatDateToYearMonthDay(
                        timeRange2
                    )
                }",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BackgroundColor),
                style = Typography.body1
            )
        }
    }
}