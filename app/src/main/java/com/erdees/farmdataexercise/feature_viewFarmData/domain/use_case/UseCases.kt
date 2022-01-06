package com.erdees.farmdataexercise.feature_viewFarmData.domain.use_case

data class UseCases(
    val getFarmData: GetFarmData,
    val addFarmData: AddFarmData,
    val getTemporaryFarmData: GetTemporaryFarmData,
    val saveTemporaryFarmData: SaveTemporaryFarmData,
    val downloadAndSaveFarmsInformation: DownloadAndSaveFarmsInformation,
    val getLocalFarmsInformation: GetLocalFarmsInformation
)