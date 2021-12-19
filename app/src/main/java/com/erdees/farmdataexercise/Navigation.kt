package com.erdees.farmdataexercise

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.erdees.farmdataexercise.coreUtils.Screen
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.farmData.FarmDataScreen
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.farmData.SelectFarmDataScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.SelectFarmDataScreen.route) {
        composable(route = Screen.SelectFarmDataScreen.route) {
            SelectFarmDataScreen(navController = navController)
        }
        composable(route = Screen.FarmDataScreen.route + "/{location}/{sensorType}/{rangeFirst}/{rangeSecond}", arguments = listOf(
            navArgument("location") {
                type = NavType.StringType
                nullable = false
            }, navArgument("sensorType") {
                type = NavType.StringType
                nullable = false
            }, navArgument("rangeFirst") {
                type = NavType.StringType
                nullable = false
            }, navArgument("rangeSecond") {
                type = NavType.StringType
                nullable = false
            })
        ) { entry ->
            FarmDataScreen(
                location = entry.arguments?.getString("location"),
            sensorType = entry.arguments?.getString("sensorType"),
            rangeFirst = entry.arguments?.getString("rangeFirst"),
            rangeSecond = entry.arguments?.getString("rangeSecond"))
        }
    }
}