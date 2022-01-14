package com.erdees.farmdataexercise.fakeRepositories

import com.erdees.farmdataexercise.feature_FarmData.domain.model.FarmData
import com.erdees.farmdataexercise.feature_FarmData.domain.repository.FarmDataRepository
import com.erdees.farmdataexercise.model.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

class FakeFarmDataRepository (): FarmDataRepository {

    private val fakeFarmData1 =
        FarmData(location = "Fake location1", datetime = "01-01-2022 15:33", "pH", "10")
    private val fakeFarmData2 =
        FarmData(location = "Fake location1", datetime = "05-01-2022 15:00", "pH", "7.5")
    private val fakeFarmData3 =
        FarmData(location = "Fake location1", datetime = "14-01-2022 07:30", "pH", "13")

    private val listOfFakeFarmData = listOf(fakeFarmData1, fakeFarmData2, fakeFarmData3)

    @ExperimentalCoroutinesApi
    override fun getFarmData(
        farmLocation: String,
        sensorType: String,
        rangeFirst: String,
        rangeSecond: String
    ): Flow<Response<List<FarmData>>> = callbackFlow {

        Response.Loading
        Thread.sleep(500)
        Response.Success(listOfFakeFarmData)
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