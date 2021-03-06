package com.erdees.farmdataexercise.coreUtils.utils

sealed class Screen(val route: String) {

    object SignUpScreen : Screen("sign_up_screen")
    object SignInScreen : Screen("sign_in_screen")
    object ProfileScreen : Screen("profile_screen")
    object SelectFarmScreen : Screen("select_farm_screen")
    object SelectFarmDataScreen : Screen("select_farm_data_screen")
    object FarmDataScreen : Screen("farm_data_screen")
    object DetailedFarmDataGraphScreen : Screen("detailed_farm_data_graph_screen")
    object AddFarmDataScreen : Screen("add_farm_data_screen")


    fun withArgs(vararg args : String) : String{
        return buildString {
            append(route)
            args.forEach {
                append("/$it")
            }
        }
    }
}