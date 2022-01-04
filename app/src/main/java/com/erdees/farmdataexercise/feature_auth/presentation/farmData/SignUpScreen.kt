package com.erdees.farmdataexercise.feature_auth.presentation.farmData

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.components.ProgressBar
import com.erdees.farmdataexercise.model.Response

@Composable
fun SignUpScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navController: NavController
) {
Column(modifier = Modifier.fillMaxWidth()) {
    when (val signUpState = viewModel.signUpState.value){
        is Response.Loading -> ProgressBar()
        is Response.Success -> Text(text = "TEST SUCCESS")
        is Response.Error -> Text(text = "Error!")
    }
}
}