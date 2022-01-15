package com.erdees.farmdataexercise.feature_farmData.data.repository


import com.erdees.farmdataexercise.FakeConstants
import com.erdees.farmdataexercise.feature_FarmData.data.local.FarmInformationDao
import com.erdees.farmdataexercise.feature_FarmData.data.local.FarmInformationEntity
import com.erdees.farmdataexercise.feature_FarmData.domain.model.FarmInformation
import com.erdees.farmdataexercise.feature_FarmData.domain.repository.FarmInfoRepository
import com.erdees.farmdataexercise.model.Response
import com.google.firebase.firestore.GeoPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow


class FakeFarmInformationRepository (private val dao: FarmInformationDao) : FarmInfoRepository {

    val farmInformation1 = FarmInformation(locationName = FakeConstants.FAKE_FARM_NAME_AND_MARKER_TITLE, geoPoint = GeoPoint(20.0,30.0),farmOwnerId = FakeConstants.FAKE_OWNER_ID)
    private val farmInformation2 = FarmInformation(locationName = "Second fake farm", geoPoint = GeoPoint(21.0,30.1),farmOwnerId = "23124asxfcas")
    private val farmInformation3 = FarmInformation(locationName = "Third fake farm", geoPoint = GeoPoint(22.0,30.2),farmOwnerId = "sadasdadsa2")

    private val farmInfoOnServer = listOf(farmInformation1,farmInformation2,farmInformation3)

    @ExperimentalCoroutinesApi
    override suspend fun downloadAndSaveFarmsInformation(): Flow<Response<List<FarmInformation>>> = callbackFlow {
        Response.Loading
        dao.insertFarmInformation(farmInfoOnServer.map { it.toFarmInformationEntity() })
       val farmInfo = (dao.getFarmInformation().map { it.toFarmInformation() })
        trySend(Response.Success(farmInfo))
        awaitClose()
    }

    override suspend fun getLocalFarmsInformation(): Flow<Response<List<FarmInformation>>> = flow {
        emit(Response.Loading)
        val farmInformation = dao.getFarmInformation().map { it.toFarmInformation() }
        emit(Response.Success(farmInformation))
    }

    override suspend fun addFarm(
        locationName: String,
        geoPoint: GeoPoint,
        userOwnerId: String
    ): Flow<Response<Void?>> = flow {
        emit(Response.Loading)
        dao.insertOneFarmInformation(FarmInformationEntity(locationName,geoPoint = geoPoint,"",userOwnerId,""))
        emit(Response.Success(null))
    }
}