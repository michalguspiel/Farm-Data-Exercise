package com.erdees.farmdataexercise.feature_FarmData.presentation.farmDataScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.erdees.farmdataexercise.R
import com.erdees.farmdataexercise.feature_FarmData.domain.model.FarmData
import com.erdees.farmdataexercise.ui.theme.BackgroundColorDarkest
import com.erdees.farmdataexercise.ui.theme.LocalSpacing
import com.erdees.farmdataexercise.ui.theme.OnPrimary
import com.erdees.farmdataexercise.ui.theme.Typography

@Composable
fun DataTable(farmDataList: List<FarmData>, modifier: Modifier) {
    LazyColumn(modifier = modifier.padding(bottom = LocalSpacing.current.default)) {
        item {
            Row(Modifier.background(BackgroundColorDarkest)) {
                TableCell(
                    text = stringResource(R.string.date_time),
                    weight = 0.7f,
                    style = Typography.h6,
                    textColor = OnPrimary
                )
                TableCell(
                    text = stringResource(R.string.value),
                    weight = 0.3f,
                    style = Typography.h6,
                    textColor = OnPrimary
                )
            }
        }
        items(farmDataList) { farmData ->
            DataRow(farmData = farmData)
        }

    }
}