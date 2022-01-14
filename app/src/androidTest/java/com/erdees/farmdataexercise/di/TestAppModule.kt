package com.erdees.farmdataexercise.di

import com.erdees.farmdataexercise.FakeConstants
import com.erdees.farmdataexercise.domain.repository.AuthRepository
import com.erdees.farmdataexercise.fakeRepositories.FakeAuthRepository
import com.erdees.farmdataexercise.fakeRepositories.FakeFarmDataRepository
import com.erdees.farmdataexercise.fakeRepositories.FakeFarmInformationRepository
import com.erdees.farmdataexercise.fakeRepositories.FakeTemporaryFarmDataRepository
import com.erdees.farmdataexercise.feature_FarmData.domain.repository.FarmDataRepository
import com.erdees.farmdataexercise.feature_FarmData.domain.repository.FarmInfoRepository
import com.erdees.farmdataexercise.feature_FarmData.domain.repository.TemporaryFarmDataRepository
import com.erdees.farmdataexercise.feature_FarmData.domain.use_case.*
import com.erdees.farmdataexercise.feature_auth.domain.use_case.*
import com.erdees.farmdataexercise.feature_auth.domain.use_case.IsUserAuthenticated
import com.erdees.farmdataexercise.feature_auth.domain.use_case.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton


@ExperimentalCoroutinesApi
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
class TestAppModule {

    @Singleton
    @Provides
    fun provideUserDocID() = FakeConstants.FAKE_OWNER_ID

    @Singleton
    @Provides
    fun provideBoolean() = false

    @Singleton
    @Provides
    fun provideFakeAuthRepository(boolean : Boolean, id : String): AuthRepository =
        FakeAuthRepository(boolean,id)


    @Singleton
    @Provides
    fun provideFakeFarmDataRepository(): FarmDataRepository =
        FakeFarmDataRepository()

    @Singleton
    @Provides
    fun provideFakeFarmInfoRepository(): FarmInfoRepository =
        FakeFarmInformationRepository()


    @Singleton
    @Provides
    fun provideFakeTemporaryFarmDataRepository() :TemporaryFarmDataRepository =
        FakeTemporaryFarmDataRepository()

    @Singleton
    @Provides
    fun provideFakeUseCases(fakeAuthRepository: FakeAuthRepository) = UseCases(
            isUserAuthenticated = IsUserAuthenticated(fakeAuthRepository),
            signOut = SignOut(fakeAuthRepository),
            getCurrentUserDocument = GetCurrentUserDocument(fakeAuthRepository),
            signInWithEmail = SignInWithEmail(fakeAuthRepository),
            signUpWithEmail = SignUpWithEmail(fakeAuthRepository)
        )

    @Singleton
    @Provides
    fun provideFakeFarmDataUseCases(fakeAuthRepository: FakeAuthRepository,
                                    fakeFarmInformationRepository: FakeFarmInformationRepository,
                                    fakeFarmDataRepository: FakeFarmDataRepository,
                                    fakeTemporaryFarmDataRepository: FakeTemporaryFarmDataRepository
    ) = com.erdees.farmdataexercise.feature_FarmData.domain.use_case.UseCases(
    getFarmData = GetFarmData(fakeFarmDataRepository),
    addFarmData = AddFarmData(fakeFarmDataRepository),
    getTemporaryFarmData = GetTemporaryFarmData(fakeTemporaryFarmDataRepository),
    saveTemporaryFarmData = SaveTemporaryFarmData(fakeTemporaryFarmDataRepository),
    downloadAndSaveFarmsInformation = DownloadAndSaveFarmsInformation(fakeFarmInformationRepository),
    getLocalFarmsInformation = GetLocalFarmsInformation(fakeFarmInformationRepository),
    isUserAuthenticated = com.erdees.farmdataexercise.feature_FarmData.domain.use_case.IsUserAuthenticated(fakeAuthRepository),
    addFarm = AddFarm(fakeFarmInformationRepository),
    getCurrentUserId = GetCurrentUserId(fakeAuthRepository)
    )


}

