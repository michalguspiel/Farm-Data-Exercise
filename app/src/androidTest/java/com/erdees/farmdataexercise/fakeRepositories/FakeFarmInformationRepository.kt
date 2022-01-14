package com.erdees.farmdataexercise.fakeRepositories

import com.erdees.farmdataexercise.FakeConstants
import com.erdees.farmdataexercise.feature_FarmData.domain.model.FarmInformation
import com.erdees.farmdataexercise.feature_FarmData.domain.repository.FarmInfoRepository
import com.erdees.farmdataexercise.model.Response
import com.google.firebase.firestore.GeoPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeFarmInformationRepository @Inject constructor() : FarmInfoRepository {

    private val farmInformation1 = FarmInformation(locationName = "First fake farm", geoPoint = GeoPoint(20.0,30.0),farmOwnerId = FakeConstants.FAKE_OWNER_ID)
    private val farmInformation2 = FarmInformation(locationName = "Second fake farm", geoPoint = GeoPoint(21.0,30.0),farmOwnerId = "23124asxfcas")
    private val farmInformation3 = FarmInformation(locationName = "Third fake farm", geoPoint = GeoPoint(22.0,30.0),farmOwnerId = "sadasdadsa2")

    @ExperimentalCoroutinesApi
    override suspend fun downloadAndSaveFarmsInformation(): Flow<Response<List<FarmInformation>>> = callbackFlow {
        Response.Loading


        Response.Success(listOf(farmInformation1,farmInformation2,farmInformation3))
        awaitClose()
    }

    override suspend fun getLocalFarmsInformation(): Flow<Response<List<FarmInformation>>> = flow {
        emit(Response.Loading)
        emit(Response.Success(listOf(farmInformation1,farmInformation2,farmInformation3)))

    }

    override suspend fun addFarm(
        locationName: String,
        geoPoint: GeoPoint,
        userOwnerId: String
    ): Flow<Response<Void?>> = flow {
            emit(Response.Loading)

            emit(Response.Success(null))
        }
    }

