package com.erdees.farmdataexercise.feature_viewFarmData.domain.repository


import com.erdees.farmdataexercise.feature_viewFarmData.domain.model.FarmInformation
import com.erdees.farmdataexercise.model.Response
import kotlinx.coroutines.flow.Flow

interface FarmInfoRepository {

    suspend fun getFarmsInformation() : Flow<Response<List<FarmInformation>>>

}