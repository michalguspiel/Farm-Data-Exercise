package com.erdees.farmdataexercise.feature_FarmData.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.erdees.farmdataexercise.R
import com.erdees.farmdataexercise.feature_FarmData.presentation.selectFarm.SelectFarmScreenViewModel
import com.erdees.farmdataexercise.ui.theme.OnPrimary


@Composable
fun AddFarmDialog(viewModel: SelectFarmScreenViewModel = hiltViewModel()) {

    var farmName by remember {
        mutableStateOf("")
    }

    if (viewModel.isOpenDialogState.value) {
        androidx.compose.material.AlertDialog(
            onDismissRequest = {
                viewModel.isOpenDialogState.value = false
            },
            title = {
                Text(text = stringResource(R.string.add_farm))
            },
            text = {
                Column {
                    TextField(
                        value = farmName,
                        onValueChange = { farmName = it },
                        placeholder = { Text(stringResource(id = R.string.farm_name)) }
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.isOpenDialogState.value = false
                        viewModel.addFarm(farmName)
                    }
                ) {
                    Text(stringResource(id = R.string.add),color = OnPrimary)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        viewModel.isOpenDialogState.value = false
                    }
                ) {
                    Text(stringResource(id = R.string.cancel),color = OnPrimary)
                }
            }
        )
    }
}