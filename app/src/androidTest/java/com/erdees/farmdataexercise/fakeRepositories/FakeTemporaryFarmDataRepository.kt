package com.erdees.farmdataexercise.fakeRepositories

import com.erdees.farmdataexercise.feature_FarmData.domain.model.FarmData
import com.erdees.farmdataexercise.feature_FarmData.domain.repository.TemporaryFarmDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeTemporaryFarmDataRepository() : TemporaryFarmDataRepository {

    private val fakeFarmData1 =
        FarmData(location = "Fake location1", datetime = "01-01-2022 15:33", "pH", "10")
    private val fakeFarmData2 =
        FarmData(location = "Fake location1", datetime = "05-01-2022 15:00", "pH", "7.5")
    private val fakeFarmData3 =
        FarmData(location = "Fake location1", datetime = "14-01-2022 07:30", "pH", "13")


    private var _temporaryFarmData: MutableStateFlow<List<FarmData>> = MutableStateFlow(emptyList())
    private val temporaryFarmData = _temporaryFarmData.asStateFlow()


    override fun getFarmData(): StateFlow<List<FarmData>> {
        return temporaryFarmData
    }

    override fun saveFarmData(farmDataList: List<FarmData>) {
        _temporaryFarmData.value = listOf(fakeFarmData1, fakeFarmData2, fakeFarmData3)
    }

}