package com.erdees.farmdataexercise.data

import com.erdees.farmdataexercise.FakeConstants
import com.erdees.farmdataexercise.FakeConstants.fakeFarmDataUser
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

class FakeAuthRepository() : AuthRepository {

    var isUserAuth : Boolean = false
    private var currUserId : String? = FakeConstants.FAKE_OWNER_ID

    override fun isUserAuthenticated(): Boolean {
        return isUserAuth
    }

    override fun getCurrentUserId(): String? {
        return currUserId
    }

    @ExperimentalCoroutinesApi
    override suspend fun getCurrentUserDocument(): Flow<Response<FarmDataUser>> = callbackFlow {
        trySend(Response.Loading)
        Thread.sleep(1000)
        if(isUserAuth){
            trySend(Response.Success(fakeFarmDataUser))
        }else{
            trySend(Response.Error("User not auth!"))
        }
        awaitClose()

    }

    override suspend fun signUpWithEmail(registration: Registration): Flow<Response<Boolean>> = flow {
        if(registration.isLegit()){
            isUserAuth = true
            currUserId =  FakeConstants.FAKE_OWNER_ID
            emit(Response.Success(true))
        } else{
            isUserAuth = false
            currUserId =  null
            emit(Response.Error("Error"))
        }
    }

    override suspend fun signInWithEmail(email: String, password: String): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        if(email == FakeConstants.FAKE_EMAIL_INPUT && password == FakeConstants.FAKE_PASSWORD_INPUT){
            isUserAuth = true
            currUserId =  FakeConstants.FAKE_OWNER_ID
            emit(Response.Success(true))
        } else{
            isUserAuth = false
            currUserId = null
            emit(Response.Error("ERROR"))
        }
    }


    override fun createUserDocument(registration: Registration): Task<Void>? {
        TODO("Not yet implemented")
    }

    override suspend fun signOut(): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        isUserAuth = false
        currUserId = null
        emit(Response.Success(true))
    }
}