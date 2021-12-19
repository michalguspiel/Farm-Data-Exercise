package com.erdees.farmdataexercise.feature_viewFarmData.domain.model

import java.time.LocalDate

data class FarmDataQuery(
    val location : String,
    val sensorType : String,
    val rangeFirst: String,
    val rangeSecond: String
) {
}