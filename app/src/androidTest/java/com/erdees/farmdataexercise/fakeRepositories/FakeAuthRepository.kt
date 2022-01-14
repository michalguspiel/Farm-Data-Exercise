package com.erdees.farmdataexercise.fakeRepositories

import com.erdees.farmdataexercise.domain.model.FarmDataUser
import com.erdees.farmdataexercise.domain.repository.AuthRepository
import com.erdees.farmdataexercise.feature_auth.domain.util.Registration
import com.erdees.farmdataexercise.model.Response
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeAuthRepository @Inject constructor(private val isUserAuth : Boolean , val currUserId : String ) : AuthRepository {


    val fakeFarmDataUser = FarmDataUser("Fake","Faker","Faker@mail.com", listOf())

    override fun isUserAuthenticated(): Boolean {
        return isUserAuth
    }

    override fun getCurrentUserId(): String? {
        return currUserId
    }

    @ExperimentalCoroutinesApi
    override suspend fun getCurrentUserDocument(): Flow<Response<FarmDataUser>> = callbackFlow {
        Response.Loading
        Thread.sleep(1000)
       if(isUserAuth) Response.Success(fakeFarmDataUser)else Response.Error("User not auth!")
        awaitClose()

    }

    override suspend fun signUpWithEmail(registration: Registration): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        Thread.sleep(1000)
        emit(Response.Success(true))
    }

    override suspend fun signInWithEmail(email: String, password: String): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        Thread.sleep(1000)
        emit(Response.Success(true))
    }


    override fun createUserDocument(registration: Registration): Task<Void>? {
        TODO("Not yet implemented")
    }

    override suspend fun signOut(): Flow<Response<Boolean>> = flow {
            emit(Response.Loading)
            Thread.sleep(1000)
            emit(Response.Success(true))
    }
    }