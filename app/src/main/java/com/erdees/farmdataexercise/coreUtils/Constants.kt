package com.erdees.farmdataexercise.coreUtils

import com.erdees.farmdataexercise.feature_viewFarmData.domain.model.PH
import com.erdees.farmdataexercise.feature_viewFarmData.domain.model.Rainfall
import com.erdees.farmdataexercise.feature_viewFarmData.domain.model.Temperature

object Constants {
    //FIRESTORE FARM DATA
    const val LOCATION = "location"
    const val LOCATION_NAME = "locationName"
    const val DATETIME = "datetime"
    const val SENSOR_TYPE = "sensorType"
    const val SENSOR_NAME = "sensorTypeName"
    const val VALUE = "value"
    const val RANGE_FIRST = "rangeFirst"
    const val RANGE_SECOND = "rangeSecond"

    const val FARM_DATA = "farm_data"
    const val DATA = "data"

    const val NOORA_FARM = "Nooras_farm"
    const val OSSI_FARM = "ossi_farm"

    val SENSOR_LIST = listOf(Temperature(),PH(),Rainfall())
    val FARM_LIST = listOf(NOORA_FARM, OSSI_FARM) // SO FAR IN HERE, LATER DOWNLOADED FROM THE SERVER AND SAVED IN SHARED PREF
}