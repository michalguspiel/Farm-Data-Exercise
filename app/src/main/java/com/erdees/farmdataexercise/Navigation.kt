package com.erdees.farmdataexercise

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.erdees.farmdataexercise.coreUtils.Constants.FARM_DEFAULT_IMAGE
import com.erdees.farmdataexercise.coreUtils.Constants.FARM_IMAGE_URL
import com.erdees.farmdataexercise.coreUtils.Constants.LOCATION_DOC_ID
import com.erdees.farmdataexercise.coreUtils.Constants.LOCATION_NAME
import com.erdees.farmdataexercise.coreUtils.Constants.RANGE_FIRST
import com.erdees.farmdataexercise.coreUtils.Constants.RANGE_SECOND
import com.erdees.farmdataexercise.coreUtils.Constants.SENSOR_NAME
import com.erdees.farmdataexercise.coreUtils.Constants.SENSOR_TYPE
import com.erdees.farmdataexercise.coreUtils.utils.Screen
import com.erdees.farmdataexercise.feature_FarmData.presentation.addFarmData.AddFarmScreen
import com.erdees.farmdataexercise.feature_FarmData.presentation.detailedFarmDataGraphScreen.DetailedFarmDataGraphScreen
import com.erdees.farmdataexercise.feature_FarmData.presentation.farmDataScreen.FarmDataScreen
import com.erdees.farmdataexercise.feature_FarmData.presentation.selectFarm.SelectFarmScreen
import com.erdees.farmdataexercise.feature_FarmData.presentation.selectFarmData.SelectFarmDataScreen
import com.erdees.farmdataexercise.feature_auth.presentation.profile.ProfileScreen
import com.erdees.farmdataexercise.feature_auth.presentation.signIn.SignInScreen
import com.erdees.farmdataexercise.feature_auth.presentation.signUp.SignUpScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.SignInScreen.route) {
        composable(route = Screen.SignUpScreen.route){
            SignUpScreen(navController = navController)
        }
        composable(route = Screen.SelectFarmScreen.route){
            SelectFarmScreen(navController = navController)
        }
        composable(route = Screen.SignInScreen.route){
            SignInScreen(navController = navController)
        }
        composable(route = Screen.AddFarmDataScreen.route + "?$LOCATION_DOC_ID={$LOCATION_DOC_ID}&$LOCATION_NAME={$LOCATION_NAME}&$FARM_IMAGE_URL={$FARM_IMAGE_URL}",arguments = listOf(
            navArgument(LOCATION_DOC_ID){
                type = NavType.StringType
                nullable = true
            },
            navArgument(LOCATION_NAME){
                type = NavType.StringType
                nullable = true
            },
            navArgument(FARM_IMAGE_URL){
                type = NavType.StringType
                defaultValue = FARM_DEFAULT_IMAGE
            }
        )) {
            AddFarmScreen(navController = navController)
        }
        composable(route = Screen.SelectFarmDataScreen.route + "?$LOCATION_DOC_ID={$LOCATION_DOC_ID}&$LOCATION_NAME={$LOCATION_NAME}&$FARM_IMAGE_URL={$FARM_IMAGE_URL}",arguments = listOf(
            navArgument(LOCATION_DOC_ID){
                type = NavType.StringType
                nullable = true
            },
            navArgument(LOCATION_NAME){
                type = NavType.StringType
                nullable = true
            },
            navArgument(FARM_IMAGE_URL){
                type = NavType.StringType
                defaultValue = FARM_DEFAULT_IMAGE
            }
        )) {
            SelectFarmDataScreen(navController = navController)
        }
        composable(route = Screen.ProfileScreen.route){
            ProfileScreen(navController = navController)
        }
        composable(route = Screen.FarmDataScreen.route + "/{$LOCATION_DOC_ID}/{$LOCATION_NAME}/{$SENSOR_TYPE}/{$RANGE_FIRST}/{$RANGE_SECOND}/{$SENSOR_NAME}", arguments = listOf(
            navArgument(LOCATION_DOC_ID) {
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
        ) {
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
        ){DetailedFarmDataGraphScreen() }
    }
}