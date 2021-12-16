package com.erdees.farmdataexercise.feature_viewFarmData.domain.model

import com.erdees.farmdataexercise.feature_viewFarmData.domain.util.ISO8601
import java.text.SimpleDateFormat
import java.util.*

data class FarmSingleDataRead(val locationName: String, val datetime: String, val sensorType: String, val value: String){

    fun toTimeStamp() : Calendar {
       return ISO8601.toCalendar(datetime)
    }

}
