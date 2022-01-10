package com.erdees.farmdataexercise.feature_viewFarmData.presentation.detailedFarmDataGraphScreen

import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.erdees.farmdataexercise.R
import com.erdees.farmdataexercise.coreUtils.Constants
import com.erdees.farmdataexercise.coreUtils.utils.Util.findActivity
import com.erdees.farmdataexercise.feature_viewFarmData.domain.util.Format
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.components.CustomDetailedLineGraph
import com.erdees.farmdataexercise.ui.theme.LocalSpacing
import com.erdees.farmdataexercise.ui.theme.Typography
import com.madrapps.plot.line.DataPoint

@Composable
fun DetailedFarmDataGraphScreen(
    viewModel: DetailedFarmDataGraphScreenViewModel = hiltViewModel()
) {

    val temporaryFarmDataList by viewModel.getTemporaryFarmData().collectAsState()

    LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = LocalSpacing.current.xSmall)
    ) {
        if (temporaryFarmDataList.isEmpty()) {
            Text(
                text = stringResource(id = R.string.oops_no_data),
                modifier = Modifier.fillMaxSize(),
                style = Typography.h3
            )
        } else {

            Text(
                text = "${viewModel.savedStateHandle.get<String>(Constants.LOCATION_NAME).toString()} ${viewModel.savedStateHandle.get<String>(Constants.SENSOR_NAME).toString()}",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                style = Typography.h4
            )
            Divider(color = Color.LightGray, thickness = 2.dp)
            val lines = temporaryFarmDataList.map {
                DataPoint(
                    Format.formatToSeconds(
                        temporaryFarmDataList[temporaryFarmDataList.indexOf(
                            it
                        )].datetime
                    ).toFloat(),
                    it.value.toFloat()
                )
            }
            CustomDetailedLineGraph(
                lines = lines,
                temporaryFarmDataList.map { it.datetime },
                temporaryFarmDataList.first().sensorType,
                Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun LockScreenOrientation(orientation: Int) {
    val context = LocalContext.current
    DisposableEffect(Unit) {
        val activity = context.findActivity() ?: return@DisposableEffect onDispose {}
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = orientation
        onDispose {
            activity.requestedOrientation = originalOrientation
        }
    }
}