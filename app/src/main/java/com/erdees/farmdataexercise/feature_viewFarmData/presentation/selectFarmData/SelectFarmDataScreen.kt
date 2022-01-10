package com.erdees.farmdataexercise.feature_viewFarmData.presentation.selectFarmData

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.erdees.farmdataexercise.R
import com.erdees.farmdataexercise.coreUtils.Constants
import com.erdees.farmdataexercise.coreUtils.Constants.FARM_DEFAULT_IMAGE
import com.erdees.farmdataexercise.coreUtils.Constants.FARM_IMAGE_URL
import com.erdees.farmdataexercise.coreUtils.Constants.LOCATION_NAME
import com.erdees.farmdataexercise.coreUtils.components.MyButton
import com.erdees.farmdataexercise.coreUtils.components.MyTopAppBar
import com.erdees.farmdataexercise.coreUtils.components.ProgressBar
import com.erdees.farmdataexercise.coreUtils.utils.Screen
import com.erdees.farmdataexercise.feature_viewFarmData.domain.util.Format.formatDate
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.components.*
import com.erdees.farmdataexercise.model.Response
import com.erdees.farmdataexercise.ui.theme.*
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
        topBar = {
            MyTopAppBar(screen = "Browse farm data", navController)
        }
    ) {
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
            Spacer(modifier = Modifier.height(LocalSpacing.current.large))
            FarmCard(
                locationName = viewModel.savedStateHandle.get<String>(LOCATION_NAME).toString(),
                farmImageUrl = viewModel.savedStateHandle.get<String>(FARM_IMAGE_URL).toString()
            )
            Spacer(Modifier.height(LocalSpacing.current.large))
            Text(text = stringResource(R.string.choose_time_range), fontSize = 20.sp)
            Calendar(modifier = Modifier.padding(horizontal = (LocalSpacing.current.xLarge)),
                calendarState = rememberSelectionState(
                    initialSelectionMode = SelectionMode.Period,
                    selectionState = CustomSelectionState({
                        timeRange = if (it.isNotEmpty()) listOf(it.first(), it.last()) else listOf()
                    }, selectionMode = SelectionMode.Period, selection = timeRange)
                ),
                monthHeader = { CalendarHeader(it) })
            Spacer(modifier = Modifier.height(LocalSpacing.current.large))


            Spinner(
                onValueChange = { sensorDocument, sensorName ->
                    sensorTypeDocument = sensorDocument
                    sensorTypeName = sensorName
                },
                textToPresent = stringResource(id = R.string.choose_metric),
                firebaseDocumentsList = Constants.SENSOR_LIST.map { it.firebaseName },
                spinnerItemsList = Constants.SENSOR_LIST.map { it.presentationName },
                modifier = Modifier.padding(
                    horizontal = (LocalSpacing.current.xLarge),
                    vertical = (LocalSpacing.current.default)
                ),
                firstColor = Yellow100,
                dropDownMenuColor = Yellow200
            )

            Spacer(modifier = Modifier.height(LocalSpacing.current.large))
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
            Spacer(modifier = Modifier.height(LocalSpacing.current.large))
        }
    }
}

@Composable
fun FarmCard(locationName: String, farmImageUrl: String) {
    Card(
        shape = RoundedCornerShape(LocalCorner.current.default),
        elevation = LocalElevation.current.large,
        modifier = Modifier.padding(LocalElevation.current.default)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(BackgroundColor)
        ) {
            Text(
                text = locationName,
                style = Typography.h4, textAlign = TextAlign.Center
            )
            Image(
                rememberImagePainter(
                    data = farmImageUrl
                ),
                contentDescription = stringResource(id = R.string.farm_pic),
                Modifier
                    .size(height = 128.dp, width = 256.dp)
                    .padding(vertical = (LocalElevation.current.default))
            )
        }
    }
}

@Composable
@Preview
fun PreviewFarmCard() {
    FarmCard("Michal's farm", FARM_DEFAULT_IMAGE)
}

