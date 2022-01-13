package com.erdees.farmdataexercise.coreUtils

import com.erdees.farmdataexercise.feature_FarmData.domain.model.PH
import com.erdees.farmdataexercise.feature_FarmData.domain.model.Rainfall
import com.erdees.farmdataexercise.feature_FarmData.domain.model.Temperature

object Constants {
    //FIRESTORE FARM DATA
    const val LOCATION_DOC_ID = "locationDocId"
    const val LOCATION_NAME = "locationName"
    const val FARM_IMAGE_URL = "farmImageUrl"
    const val DATETIME = "datetime"
    const val SENSOR_TYPE = "sensorType"
    const val SENSOR_NAME = "sensorTypeName"
    const val RANGE_FIRST = "rangeFirst"
    const val RANGE_SECOND = "rangeSecond"


    const val FARM_DEFAULT_IMAGE = "https://images.pexels.com/photos/1108807/pexels-photo-1108807.jpeg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=1260"

    const val DATA = "data"

    const val NOORA_FARM = "Nooras_farm"

    val SENSOR_LIST = listOf(Temperature(),PH(),Rainfall())

    //MESSAGES
    const val ERROR_MESSAGE = "Error"

    // STRINGS
    const val SIGN_UP_TAG = "sign_up"
    const val SIGN_IN_TAG = "sign_in"
    const val CONTINUE_TAG = "continue"

}