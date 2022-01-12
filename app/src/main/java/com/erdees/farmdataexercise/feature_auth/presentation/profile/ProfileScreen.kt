package com.erdees.farmdataexercise.feature_auth.presentation.profile

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdees.farmdataexercise.R
import com.erdees.farmdataexercise.coreUtils.components.MyButton
import com.erdees.farmdataexercise.coreUtils.components.MyTopAppBar
import com.erdees.farmdataexercise.coreUtils.components.ProgressBar
import com.erdees.farmdataexercise.coreUtils.utils.Screen
import com.erdees.farmdataexercise.model.Response
import com.erdees.farmdataexercise.ui.theme.LocalSpacing
import com.erdees.farmdataexercise.ui.theme.Typography
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        if (!profileViewModel.isUserAuthenticated) navController.navigate(Screen.SignInScreen.route) {
            popUpTo(
                Screen.SelectFarmDataScreen.route
            )
        }
        else profileViewModel.getCurrentUserDocument()
    }

    if (profileViewModel.isUserAuthenticated) {
        Scaffold(topBar = { MyTopAppBar(screen = Screen.ProfileScreen, navController = navController) }) {
            when (val response = profileViewModel.isUserSignedOutState.value) {
                is Response.Loading -> ProgressBar()
                is Response.Success -> {
                    if (response.data) {
                        navController.popBackStack()
                        navController.navigate(Screen.SignUpScreen.route)
                    }
                }
                is Response.Error -> Log.d("profile_screen", response.message)
            }

            when (val userDocResponse = profileViewModel.currentUserDocument.value) {
                is Response.Loading -> ProgressBar()
                is Response.Success -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(LocalSpacing.current.xLarge))
                        Text(text = stringResource(id = R.string.hello),
                            style = Typography.h4)
                        Spacer(modifier = Modifier.height(LocalSpacing.current.default))
                        Text(text = "${userDocResponse.data.firstName} ${userDocResponse.data.lastName}",
                        modifier = Modifier,
                        style = Typography.h3)
                        Spacer(modifier = Modifier.height(LocalSpacing.current.default))
                        Text(text = userDocResponse.data.email,
                            style = Typography.body1)
                        Spacer(modifier = Modifier.height(LocalSpacing.current.xLarge))
                        MyButton(onClick = {
                            profileViewModel.signOut()
                        },text = stringResource(id = R.string.sign_out))
                    }
                }
                is Response.Error -> Log.i("profile_screen", userDocResponse.message)
            }
        }
    }
}