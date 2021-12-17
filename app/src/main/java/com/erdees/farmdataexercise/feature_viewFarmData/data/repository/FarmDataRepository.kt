package com.erdees.farmdataexercise.feature_viewFarmData.data.repository

import com.erdees.farmdataexercise.feature_viewFarmData.domain.model.FarmSingleDataRead
import com.erdees.farmdataexercise.feature_viewFarmData.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface FarmDataRepository {

    fun getFarmData(farmLocation :String) : Flow<Response<List<FarmSingleDataRead>>>

    suspend fun addFarmData(locationName : String,dateTime: String, sensorType:String,value: String ) : Flow<Response<Void?>>


}