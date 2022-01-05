package com.erdees.farmdataexercise.feature_auth.presentation.signIn

import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdees.farmdataexercise.coreUtils.utils.Screen
import com.erdees.farmdataexercise.feature_auth.presentation.signIn.components.SignInContent
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.components.ProgressBar
import com.erdees.farmdataexercise.model.Response

@Composable
fun SignInScreen(signInViewModel: SignInViewModel = hiltViewModel(),navController: NavController) {

    LaunchedEffect(Unit){
        if(signInViewModel.isUserAuthenticated)navController.navigate(Screen.ProfileScreen.route)
    }

    if (!signInViewModel.isUserAuthenticated){
        Scaffold() {
            SignInContent(navController = navController)
            when (val response = signInViewModel.signInState.value){
                is Response.Loading -> ProgressBar()
                is Response.Success ->{
                    if (response.data)
                    {
                        navController.navigate(Screen.ProfileScreen.route){
                            popUpTo(Screen.SelectFarmDataScreen.route)
                        }
                    }
                }
                is Response.Error -> Log.i("sign_in_screen",response.message)
            }
        }
    }



}