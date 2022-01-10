package com.erdees.farmdataexercise.feature_auth.presentation.signIn

import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdees.farmdataexercise.R
import com.erdees.farmdataexercise.coreUtils.components.ProgressBar
import com.erdees.farmdataexercise.coreUtils.utils.Screen
import com.erdees.farmdataexercise.feature_auth.presentation.signIn.components.SignInContent
import com.erdees.farmdataexercise.model.Response

@Composable
fun SignInScreen(signInViewModel: SignInViewModel = hiltViewModel(), navController: NavController) {

    val openDialogState = remember {
        mutableStateOf(false)
    }

    var errorMessageState by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        if (signInViewModel.isUserAuthenticated) navController.navigate(Screen.ProfileScreen.route)
    }

    if (!signInViewModel.isUserAuthenticated) {
        Scaffold {

            if (openDialogState.value) {
                androidx.compose.material.AlertDialog(onDismissRequest = {
                    openDialogState.value = false
                },
                    title = { Text(text = stringResource(id = R.string.error)) },
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

            SignInContent(navController = navController)
            when (val response = signInViewModel.signInState.value) {
                is Response.Loading -> ProgressBar()
                is Response.Success -> {
                    if (response.data) {
                        navController.navigate(Screen.ProfileScreen.route) {
                            popUpTo(Screen.SelectFarmDataScreen.route)
                        }
                    }
                }
                is Response.Error -> {
                    errorMessageState = response.message
                    openDialogState.value = true
                    signInViewModel.resetSignInState()
                }
            }


        }
    }


}