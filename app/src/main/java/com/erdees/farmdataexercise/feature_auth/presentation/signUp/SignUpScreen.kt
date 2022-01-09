package com.erdees.farmdataexercise.feature_auth.presentation.signUp

import android.util.Log
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdees.farmdataexercise.R
import com.erdees.farmdataexercise.coreUtils.utils.Screen
import com.erdees.farmdataexercise.feature_auth.presentation.signUp.components.SignUpContent
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.components.ProgressBar
import com.erdees.farmdataexercise.model.Response

@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    navController: NavController
) {
    val openDialogState = remember {
        mutableStateOf(false)
    }

    var errorMessageState by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        if (signUpViewModel.isUserAuthenticated) {
            navController.navigate(Screen.SelectFarmDataScreen.route)
        }
    }

    if (!signUpViewModel.isUserAuthenticated) {
        Scaffold(
        ) {

            if(openDialogState.value){
                androidx.compose.material.AlertDialog(onDismissRequest = { openDialogState.value = false },
                    title = { Text(text = "Error") },
                    text = { Text(text = errorMessageState) },
                    confirmButton = {},
                    dismissButton = {
                        Button(

                        onClick = {
                            openDialogState.value = false
                        }) {
                        Text(stringResource(R.string.back))
                    }
                    }
                )
            }

            SignUpContent(navController = navController)

            when(val response = signUpViewModel.signUpState.value) {
                is Response.Loading -> ProgressBar()
                is Response.Success -> {
                    if (response.data) {
                        navController.navigate(Screen.ProfileScreen.route){
                            popUpTo(Screen.SelectFarmDataScreen.route)
                        }
                    }
                }
                is Response.Error ->{
                    errorMessageState = response.message
                    openDialogState.value = true
                    signUpViewModel.resetSignUpState()
                    Log.d("sign_up_screen", response.message)
                }
            }
        }
    }
}