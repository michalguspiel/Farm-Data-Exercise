package com.erdees.farmdataexercise.coreUtils

object Constants {
    //FIRESTORE FARM DATA
    const val LOCATION = "location"
    const val DATETIME = "datetime"
    const val SENSOR_TYPE = "sensorType"
    const val VALUE = "value"

    const val FARM_DATA = "farm_data"
    const val DATA = "data"

    const val NOORA_FARM = "Nooras_farm"
    const val OSSI_FARM = "ossi_farm"

    val SENSOR_LIST = listOf("pH","temperature","rainFall")
    val FARM_LIST = listOf(NOORA_FARM, OSSI_FARM) // SO FAR IN HERE, LATER DOWNLOADED FROM THE SERVER AND SAVED IN SHARED PREF
}