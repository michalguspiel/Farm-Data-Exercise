package com.erdees.farmdataexercise.feature_auth.presentation.signUp

import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdees.farmdataexercise.coreUtils.utils.Screen
import com.erdees.farmdataexercise.feature_auth.presentation.signUp.components.SignUpContent
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.components.ProgressBar
import com.erdees.farmdataexercise.model.Response

@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    navController: NavController
) {
    LaunchedEffect(Unit) {
        if (signUpViewModel.isUserAuthenticated) {
            navController.navigate(Screen.SelectFarmDataScreen.route)
        }
    }

    if (!signUpViewModel.isUserAuthenticated) {
        Scaffold(
        ) {
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
                is Response.Error -> Log.d("sign_up_screen", response.message)
            }
        }
    }
}