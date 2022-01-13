package com.erdees.farmdataexercise.feature_FarmData.presentation.addFarmData.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.erdees.farmdataexercise.R
import com.erdees.farmdataexercise.coreUtils.Constants
import com.erdees.farmdataexercise.coreUtils.components.MyButton
import com.erdees.farmdataexercise.feature_FarmData.domain.util.Util.isNumber
import com.erdees.farmdataexercise.feature_FarmData.presentation.addFarmData.AddFarmScreenViewModel
import com.erdees.farmdataexercise.feature_FarmData.presentation.components.FarmCard
import com.erdees.farmdataexercise.feature_FarmData.presentation.components.Spinner
import com.erdees.farmdataexercise.ui.theme.*

@Composable
fun AddFarmContent(
    viewModel: AddFarmScreenViewModel = hiltViewModel(),
) {

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
            locationName = viewModel.savedStateHandle.get<String>(Constants.LOCATION_NAME)
                .toString(),
            farmImageUrl = viewModel.savedStateHandle.get<String>(Constants.FARM_IMAGE_URL)
                .toString()
        )


        Column(
            Modifier
                .width(IntrinsicSize.Max)
                .padding(horizontal = LocalSpacing.current.xLarge),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(LocalSpacing.current.large))

            Spinner(
                onValueChange = { sensorDocument, sensorName ->
                    viewModel.sensorTypeDocument.value = sensorDocument
                    viewModel.sensorTypeName.value = sensorName
                },
                textToPresent = stringResource(id = R.string.choose_metric),
                firebaseDocumentsList = Constants.SENSOR_LIST.map { it.firebaseName },
                spinnerItemsList = Constants.SENSOR_LIST.map { it.presentationName },
                modifier = Modifier.padding(
                    vertical = (LocalSpacing.current.default)
                ),
                firstColor = Yellow100,
                dropDownMenuColor = Yellow200
            )
            Spacer(modifier = Modifier.height(LocalSpacing.current.default))

            Text(text = stringResource(id = R.string.date), style = Typography.caption)
            OutlinedButton(
                onClick = { viewModel.isDatePickerShown.value = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (viewModel.pickedDate.value == "") stringResource(id = R.string.pick_date) else viewModel.pickedDate.value)
            }

            Spacer(modifier = Modifier.height(LocalSpacing.current.large))
            Text(text = stringResource(id = R.string.time), style = Typography.caption)
            OutlinedButton(
                onClick = { viewModel.isTimePickerShown.value = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (viewModel.pickedTime.value == "") stringResource(id = R.string.pick_time) else viewModel.pickedTime.value)
            }

            Spacer(modifier = Modifier.height(LocalSpacing.current.large))

            Text(text = stringResource(id = R.string.data_value), style = Typography.caption)
            OutlinedTextField(
                value = viewModel.dataValue.value, onValueChange = { string ->
                    if (isNumber(string)) viewModel.dataValue.value = string
                }, modifier = Modifier.width(110.dp),
                textStyle = Typography.body2,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(LocalCorner.current.default),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = OnPrimaryLightest,
                    backgroundColor = Yellow300
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(LocalSpacing.current.large))

            MyButton(
                onClick = { viewModel.addFarmData() },
                text =
                stringResource(id = R.string.add_data), modifier = Modifier.fillMaxWidth(),
                enabled = (isTimeAndDatePicked(viewModel = viewModel) && viewModel.sensorTypeDocument.value != "" && viewModel.dataValue.value != "")
            )
        }
    }
}

fun isTimeAndDatePicked(viewModel: AddFarmScreenViewModel): Boolean {
    return (viewModel.pickedDate.value != "" && viewModel.pickedTime.value != "")
}