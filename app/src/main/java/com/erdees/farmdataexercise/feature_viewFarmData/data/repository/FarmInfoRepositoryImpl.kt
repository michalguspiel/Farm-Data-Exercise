package com.erdees.farmdataexercise.feature_viewFarmData.data.repository

import android.util.Log
import com.erdees.farmdataexercise.feature_viewFarmData.data.local.FarmInformationDao
import com.erdees.farmdataexercise.feature_viewFarmData.domain.model.FarmInformation
import com.erdees.farmdataexercise.feature_viewFarmData.domain.repository.FarmInfoRepository
import com.erdees.farmdataexercise.model.Response
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@ExperimentalCoroutinesApi
class FarmInfoRepositoryImpl @Inject constructor(
    private val dao: FarmInformationDao,
    @Named("farmData") private val farmDataReference: CollectionReference,
) : FarmInfoRepository {

    override suspend fun getFarmsInformation(): Flow<Response<List<FarmInformation>>> = callbackFlow {
        Log.i("FarmInfoRepo", "GetFarmInfo launched!")
        Response.Loading
        val query = farmDataReference.get().await()
        Log.i("FarmInfoRepo","Get farm info finished waiting for query")


        val remoteFarmInformation = query.toObjects(FarmInformation::class.java)
        if (remoteFarmInformation.isNotEmpty()){
            dao.deleteFarmInformation()
            dao.insertFarmInformation(remoteFarmInformation.map{it.toFarmInformationEntity()})
        }
        else {
            Response.Error("List was empty!")
        }
        val newFarmInformation = dao.getFarmInformation().map { it.toFarmInformation() }
        trySend(Response.Success(newFarmInformation))
        awaitClose { cancel("", null) }

    }
}
