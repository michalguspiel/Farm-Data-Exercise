package com.erdees.farmdataexercise.feature_FarmData.domain.repository

import com.erdees.farmdataexercise.feature_FarmData.domain.model.FarmData
import com.erdees.farmdataexercise.model.Response
import kotlinx.coroutines.flow.Flow

interface FarmDataRepository {

    fun getFarmData(farmLocation :String,sensorType: String,rangeFirst: String, rangeSecond: String) : Flow<Response<List<FarmData>>>

    suspend fun addFarmData(locationName : String,dateTime: String, sensorType:String,value: String ) : Flow<Response<Void?>>

}