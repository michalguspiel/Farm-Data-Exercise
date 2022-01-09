package com.erdees.farmdataexercise.feature_viewFarmData.presentation.selectFarmData

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.erdees.farmdataexercise.R
import com.erdees.farmdataexercise.coreUtils.Constants
import com.erdees.farmdataexercise.coreUtils.Constants.FARM_IMAGE_URL
import com.erdees.farmdataexercise.coreUtils.Constants.LOCATION_NAME
import com.erdees.farmdataexercise.coreUtils.components.MyButton
import com.erdees.farmdataexercise.coreUtils.components.MyTopAppBar
import com.erdees.farmdataexercise.coreUtils.utils.Screen
import com.erdees.farmdataexercise.feature_viewFarmData.domain.util.Format.formatDate
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.components.*
import com.erdees.farmdataexercise.model.Response
import com.erdees.farmdataexercise.ui.theme.Typography
import io.github.boguszpawlowski.composecalendar.Calendar
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode
import kotlinx.coroutines.InternalCoroutinesApi
import java.time.LocalDate

@InternalCoroutinesApi
@Composable
fun SelectFarmDataScreen(
    viewModel: SelectFarmDataViewModel = hiltViewModel(),
    navController: NavController
) {

    var sensorTypeDocument by remember {
        mutableStateOf("")
    }

    var sensorTypeName by remember {
        mutableStateOf("")
    }

    var timeRange by remember {
        mutableStateOf(listOf<LocalDate>())
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.openDialogState.value = true
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(
                        R.string.add_farm_data
                    )
                )
            }
        },
        topBar = {
            MyTopAppBar(screen = "Browse farm data", navController)
        }
    ) {
        if (viewModel.openDialogState.value) {
            AlertDialog()
        }
        when (val additionResponse = viewModel.isFarmDataAddedState.value) {
            is Response.Empty -> {
            }
            is Response.Loading -> ProgressBar()
            is Response.Success -> Toast(stringResource(R.string.data_added_successfully))
            is Error -> Toast(additionResponse.message)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = viewModel.savedStateHandle.get<String>(LOCATION_NAME).toString(),
                style = Typography.h4, textAlign = TextAlign.Center
            )
            Image(
                rememberImagePainter(
                    data = viewModel.savedStateHandle.get<String>(FARM_IMAGE_URL).toString()
                ),
                contentDescription = "Farm picture",
                Modifier.size(height = 128.dp, width = 256.dp)
            )
            Text(text = stringResource(R.string.choose_time_range), fontSize = 20.sp)
            Calendar(modifier = Modifier.padding(horizontal = 30.dp),
                calendarState = rememberSelectionState(
                    initialSelectionMode = SelectionMode.Period,
                    selectionState = CustomSelectionState({
                        timeRange = if (it.isNotEmpty()) listOf(it.first(), it.last()) else listOf()
                    }, selectionMode = SelectionMode.Period, selection = timeRange)
                ),
                monthHeader = { CalendarHeader(it) })
            Spacer(modifier = Modifier.height(26.dp))

            Text(text = stringResource(R.string.choose_metric), fontSize = 20.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Spinner(
                onValueChange = { sensorDocument, sensorName ->
                    sensorTypeDocument = sensorDocument
                    sensorTypeName = sensorName
                },
                textToPresent = stringResource(id = R.string.sensorType),
                firebaseDocumentsList = Constants.SENSOR_LIST.map { it.firebaseName },
                spinnerItemsList = Constants.SENSOR_LIST.map { it.presentationName },
                modifier = Modifier.padding(horizontal = 40.dp),
                Color.Gray,
                Color.LightGray
            )
            Spacer(modifier = Modifier.height(20.dp))
            MyButton(
                onClick = {
                    navController.navigate(
                        Screen.FarmDataScreen.withArgs(
                            viewModel.savedStateHandle.get<String>(Constants.LOCATION_DOC_ID)
                                .toString(),
                            viewModel.savedStateHandle.get<String>(LOCATION_NAME)
                                .toString(),
                            sensorTypeDocument,
                            formatDate(timeRange.first()),
                            formatDate(timeRange[1]),
                            sensorTypeName
                        )
                    )
                },
                enabled = !(sensorTypeDocument == "" || timeRange.isEmpty()),
                text = stringResource(R.string.show_data)
            )
            Spacer(modifier = Modifier.height(20.dp))


        }
    }
}

