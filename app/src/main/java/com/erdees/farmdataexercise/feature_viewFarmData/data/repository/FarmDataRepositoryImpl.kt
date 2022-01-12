package com.erdees.farmdataexercise.feature_viewFarmData.data.repository

import com.erdees.farmdataexercise.coreUtils.Constants
import com.erdees.farmdataexercise.feature_viewFarmData.domain.model.FarmData
import com.erdees.farmdataexercise.feature_viewFarmData.domain.repository.FarmDataRepository
import com.erdees.farmdataexercise.model.Response
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@ExperimentalCoroutinesApi
class FarmDataRepositoryImpl @Inject constructor(
    @Named("farmData") private val farmDataReference: CollectionReference,
) : FarmDataRepository {


    override fun getFarmData(
        farmLocation: String,
        sensorType: String,
        rangeFirst: String,
        rangeSecond: String
    ) = callbackFlow {
        val query = farmDataReference.document(farmLocation).collection(Constants.DATA)
            .whereEqualTo(Constants.SENSOR_TYPE, sensorType)
            .whereGreaterThan(Constants.DATETIME, rangeFirst)
            .whereLessThan(Constants.DATETIME, rangeSecond)

        val snapListener = query.addSnapshotListener { snapshot, error ->
            val response = if (snapshot != null) {
                val farmData = snapshot.toObjects(FarmData::class.java)
                Response.Success(farmData)
            } else {
                Response.Error(error?.message ?: error.toString())
            }
            trySend(response).isSuccess
        }
        awaitClose {
            snapListener.remove()
        }
    }

    override suspend fun addFarmData(
        locationName: String,
        dateTime: String,
        sensorType: String,
        value: String
    ): Flow<Response<Void?>> = flow {
        try {
            emit(Response.Loading)
            val farmData = FarmData(locationName, dateTime, sensorType, value)
            val newDocumentId =
                farmDataReference.document(locationName).collection("data").document()
            val addition = newDocumentId.set(farmData).await()
            emit(Response.Success(addition))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }


}

