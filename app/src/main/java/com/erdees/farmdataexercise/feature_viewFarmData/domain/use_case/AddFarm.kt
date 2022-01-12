package com.erdees.farmdataexercise.feature_viewFarmData.domain.use_case

import com.erdees.farmdataexercise.feature_viewFarmData.domain.repository.FarmInfoRepository
import com.google.firebase.firestore.GeoPoint

class AddFarm(private val repository: FarmInfoRepository) {
    suspend operator fun invoke(locationName: String, geoPoint: GeoPoint, userOwnerId: String ) = repository.addFarm(locationName = locationName,geoPoint = geoPoint,userOwnerId = userOwnerId)
}