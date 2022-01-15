package com.erdees.farmdataexercise.feature_farmData.data.repository


import com.erdees.farmdataexercise.FakeConstants.FAKE_LOCATION
import com.erdees.farmdataexercise.FakeConstants.PH
import com.erdees.farmdataexercise.feature_FarmData.domain.model.FarmData
import com.erdees.farmdataexercise.feature_FarmData.domain.repository.FarmDataRepository
import com.erdees.farmdataexercise.model.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

class FakeFarmDataRepository : FarmDataRepository {

    private val fakeFarmData1 =
        FarmData(location = FAKE_LOCATION, datetime = "2018-12-31T22:00:00.000", PH, "10")
    private val fakeFarmData2 =
        FarmData(location = FAKE_LOCATION, datetime = "2018-12-29T22:00:00.000", PH, "7.5")
    private val fakeFarmData3 =
        FarmData(location = FAKE_LOCATION, datetime = "2018-12-30T22:00:00.000", PH, "13")


    @ExperimentalCoroutinesApi
    override fun getFarmData(
        farmLocation: String,
        sensorType: String,
        rangeFirst: String,
        rangeSecond: String
    ): Flow<Response<List<FarmData>>> = callbackFlow {

        trySend(Response.Success(listOf(fakeFarmData1, fakeFarmData2, fakeFarmData3)))

        awaitClose()

    }

    override suspend fun addFarmData(
        locationName: String,
        dateTime: String,
        sensorType: String,
        value: String
    ): Flow<Response<Void?>> = flow {
        emit(Response.Loading)
        Thread.sleep(500)
        emit(Response.Success(null))
    }
}