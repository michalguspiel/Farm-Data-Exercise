package com.erdees.farmdataexercise.feature_FarmData.presentation.farmDataScreen.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.erdees.farmdataexercise.R
import com.erdees.farmdataexercise.coreUtils.components.MyButton
import com.erdees.farmdataexercise.ui.theme.LocalSpacing
import com.erdees.farmdataexercise.ui.theme.Typography

@Composable
fun NoDataContent(navController: NavController) {
    Spacer(Modifier.height(LocalSpacing.current.xLarge))
    Text(
        text = stringResource(id = R.string.oops_no_data),
        modifier = Modifier
            .fillMaxWidth()
            .padding(LocalSpacing.current.default),
        textAlign = TextAlign.Center,
        style = Typography.h4
    )
    Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
    MyButton(onClick = { navController.popBackStack() }, text = stringResource(id = R.string.back))
}