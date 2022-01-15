package com.erdees.farmdataexercise.di

import android.app.Application
import androidx.room.Room
import com.erdees.farmdataexercise.data.FakeAuthRepository
import com.erdees.farmdataexercise.feature_FarmData.data.local.FarmInformationDatabase
import com.erdees.farmdataexercise.feature_FarmData.domain.use_case.*
import com.erdees.farmdataexercise.feature_auth.domain.use_case.*
import com.erdees.farmdataexercise.feature_auth.domain.use_case.IsUserAuthenticated
import com.erdees.farmdataexercise.feature_auth.domain.use_case.UseCases
import com.erdees.farmdataexercise.feature_farmData.data.repository.FakeFarmDataRepository
import com.erdees.farmdataexercise.feature_farmData.data.repository.FakeFarmInformationRepository
import com.erdees.farmdataexercise.feature_farmData.data.repository.FakeTemporaryFarmDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
class TestAppModule {


    @Singleton
    @Provides
    fun provideFakeAuthRepository(): FakeAuthRepository =
        FakeAuthRepository()


    @Provides
    fun provideFakeFarmDataRepository(): FakeFarmDataRepository =
        FakeFarmDataRepository()

    @Provides
    fun provideFakeFarmInfoRepository(
        db: FarmInformationDatabase,
    ): FakeFarmInformationRepository = FakeFarmInformationRepository(
        dao = db.farmInformationDao,
    )

    @Provides
    fun provideFarmInformationDatabase(app: Application): FarmInformationDatabase {
        return Room.inMemoryDatabaseBuilder(app, FarmInformationDatabase::class.java).build()
    }


    @Provides
    fun provideFakeTemporaryFarmDataRepository() : FakeTemporaryFarmDataRepository =
        FakeTemporaryFarmDataRepository()

    @Provides
    fun provideFakeUseCases(fakeAuthRepository: FakeAuthRepository) = UseCases(
        isUserAuthenticated = IsUserAuthenticated(fakeAuthRepository),
        signOut = SignOut(fakeAuthRepository),
        getCurrentUserDocument = GetCurrentUserDocument(fakeAuthRepository),
        signInWithEmail = SignInWithEmail(fakeAuthRepository),
        signUpWithEmail = SignUpWithEmail(fakeAuthRepository)
    )

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