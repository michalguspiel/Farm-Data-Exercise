package com.erdees.farmdataexercise.coreUtils

sealed class Screen(val route: String) {

    object SelectFarmDataScreen : Screen("select_farm_data_screen")
    object FarmDataScreen : Screen("farm_data_screen")
    object DetailedFarmDataGraphScreen : Screen("detailed_farm_data_graph_screen")


    fun withArgs(vararg args : String) : String{
        return buildString {
            append(route)
            args.forEach {
                append("/$it")
            }
        }
    }
}