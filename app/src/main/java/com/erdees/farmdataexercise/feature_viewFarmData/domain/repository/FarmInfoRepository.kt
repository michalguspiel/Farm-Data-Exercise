package com.erdees.farmdataexercise.feature_viewFarmData.domain.repository


import com.erdees.farmdataexercise.feature_viewFarmData.domain.model.FarmInformation
import com.erdees.farmdataexercise.model.Response
import com.google.firebase.firestore.GeoPoint
import kotlinx.coroutines.flow.Flow

interface FarmInfoRepository {

    suspend fun downloadAndSaveFarmsInformation() : Flow<Response<List<FarmInformation>>>

    suspend fun getLocalFarmsInformation() : Flow<Response<List<FarmInformation>>>

    suspend fun addFarm(locationName : String, geoPoint: GeoPoint, userOwnerId: String) : Flow<Response<Void?>>


}