package com.erdees.farmdataexercise.feature_FarmData.domain.use_case

data class UseCases(
    val getFarmData: GetFarmData,
    val addFarmData: AddFarmData,
    val addFarm : AddFarm,
    val getTemporaryFarmData: GetTemporaryFarmData,
    val saveTemporaryFarmData: SaveTemporaryFarmData,
    val downloadAndSaveFarmsInformation: DownloadAndSaveFarmsInformation,
    val getLocalFarmsInformation: GetLocalFarmsInformation,
    val isUserAuthenticated: IsUserAuthenticated,
    val getCurrentUserId : GetCurrentUserId
)