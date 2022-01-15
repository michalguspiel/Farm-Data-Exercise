package com.erdees.farmdataexercise.feature_auth.presentation.signUp

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdees.farmdataexercise.R
import com.erdees.farmdataexercise.coreUtils.TestTags.ALERT_DIALOG_TAG
import com.erdees.farmdataexercise.coreUtils.components.MyButton
import com.erdees.farmdataexercise.coreUtils.components.ProgressBar
import com.erdees.farmdataexercise.coreUtils.utils.Screen
import com.erdees.farmdataexercise.feature_auth.presentation.components.AlertDialog
import com.erdees.farmdataexercise.feature_auth.presentation.signUp.components.SignUpContent
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
        Scaffold {

            if (openDialogState.value) {
                AlertDialog(
                    modifier = Modifier.testTag(ALERT_DIALOG_TAG),
                    onDismissRequest = { openDialogState.value = false },
                    title = { Text(text = stringResource(R.string.error)) },
                    text = { Text(text = errorMessageState) },
                    dismissButton = {
                        MyButton(
                            onClick = {
                                openDialogState.value = false
                            }, text = stringResource(id = R.string.back)
                        )
                    })
            }

            SignUpContent(navController = navController)
            when (val response = signUpViewModel.signUpState.value) {
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
                    signUpViewModel.resetSignUpState()
                }
            }

        }
    }
}
