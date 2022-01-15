package com.erdees.farmdataexercise.feature_auth.presentation.profile

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdees.farmdataexercise.R
import com.erdees.farmdataexercise.coreUtils.TestTags
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
        Scaffold(topBar = {
            MyTopAppBar(
                screen = Screen.ProfileScreen,
                navController = navController
            )
        }) {
            when (val response = profileViewModel.isUserSignedOutState.value) {
                is Response.Loading -> ProgressBar()
                is Response.Success -> {
                    if (response.data) {
                        navController.popBackStack()
                        navController.navigate(Screen.SignInScreen.route)
                    }
                }
                is Response.Error -> Log.d("profile_screen", response.message)
            }

            when (val userDocResponse = profileViewModel.currentUserDocument.value) {
                is Response.Loading -> ProgressBar()
                is Response.Success -> {

                    ConstraintLayout(modifier = Modifier.fillMaxSize().testTag(TestTags.PROFILE_SCREEN_TAG)) {
                        val (column, button) = createRefs()
                        Column(
                            modifier = Modifier.constrainAs(column){
                                                                   top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.height(LocalSpacing.current.xLarge))
                            Text(
                                text = stringResource(id = R.string.hello),
                                style = Typography.h4
                            )
                            Spacer(modifier = Modifier.height(LocalSpacing.current.default))
                            Text(
                                text = "${userDocResponse.data.firstName} ${userDocResponse.data.lastName}",
                                modifier = Modifier,
                                style = Typography.h3
                            )
                            Spacer(modifier = Modifier.height(LocalSpacing.current.default))
                            Text(
                                text = userDocResponse.data.email,
                                style = Typography.body1
                            )
                            Spacer(modifier = Modifier.height(LocalSpacing.current.xLarge))
                            MyButton(onClick = {
                                navController.navigate(Screen.SelectFarmScreen.route)
                            },text = stringResource(id = R.string.browse_farm_data ))
                        }
                        MyButton(
                            onClick = {
                                profileViewModel.signOut()
                            },
                            text = stringResource(id = R.string.sign_out),
                            modifier = Modifier
                                .constrainAs(button) {
                                    bottom.linkTo(parent.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }.testTag(TestTags.PROFILE_SCREEN_SIGN_OUT_BTN)
                                .padding(bottom = LocalSpacing.current.xLarge))
                    }
                }
                is Response.Error -> Log.i("profile_screen", userDocResponse.message)
            }
        }
    }
}