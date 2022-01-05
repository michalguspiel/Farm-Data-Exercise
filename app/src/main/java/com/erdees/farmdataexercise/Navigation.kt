package com.erdees.farmdataexercise

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.erdees.farmdataexercise.coreUtils.Constants.LOCATION
import com.erdees.farmdataexercise.coreUtils.Constants.LOCATION_NAME
import com.erdees.farmdataexercise.coreUtils.Constants.RANGE_FIRST
import com.erdees.farmdataexercise.coreUtils.Constants.RANGE_SECOND
import com.erdees.farmdataexercise.coreUtils.Constants.SENSOR_NAME
import com.erdees.farmdataexercise.coreUtils.Constants.SENSOR_TYPE
import com.erdees.farmdataexercise.coreUtils.utils.Screen
import com.erdees.farmdataexercise.feature_auth.presentation.profile.ProfileScreen
import com.erdees.farmdataexercise.feature_auth.presentation.signIn.SignInScreen
import com.erdees.farmdataexercise.feature_auth.presentation.signUp.SignUpScreen
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.farmData.DetailedFarmDataGraphScreen
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.farmData.FarmDataScreen
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.farmData.SelectFarmDataScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.SignInScreen.route) {
        composable(route = Screen.SignUpScreen.route){
            SignUpScreen(navController = navController)
        }
        composable(route = Screen.SignInScreen.route){
            SignInScreen(navController = navController)
        }
        composable(route = Screen.SelectFarmDataScreen.route) {
            SelectFarmDataScreen(navController = navController)
        }
        composable(route = Screen.ProfileScreen.route){
            ProfileScreen(navController = navController)
        }
        composable(route = Screen.FarmDataScreen.route + "/{$LOCATION}/{$LOCATION_NAME}/{$SENSOR_TYPE}/{$RANGE_FIRST}/{$RANGE_SECOND}/{$SENSOR_NAME}", arguments = listOf(
            navArgument(LOCATION) {
                type = NavType.StringType
                nullable = false
            },navArgument(LOCATION_NAME) {
                type = NavType.StringType
                nullable = false
            }, navArgument(SENSOR_TYPE) {
                type = NavType.StringType
                nullable = false
            }, navArgument(RANGE_FIRST) {
                type = NavType.StringType
                nullable = false
            }, navArgument(RANGE_SECOND) {
                type = NavType.StringType
                nullable = false
            }, navArgument(SENSOR_NAME) {
            type = NavType.StringType
            nullable = false
        })
        ) { entry ->
            FarmDataScreen(navController = navController)
        }
        composable(route = Screen.DetailedFarmDataGraphScreen.route + "/{$LOCATION_NAME}/{$SENSOR_NAME}", arguments = listOf(
            navArgument(LOCATION_NAME){
                type = NavType.StringType
                nullable = false
            },navArgument(SENSOR_NAME){
                type = NavType.StringType
                nullable = false
            }
        )
        ){entry -> DetailedFarmDataGraphScreen()}
    }
}