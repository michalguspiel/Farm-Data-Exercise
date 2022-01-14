package com.erdees.farmdataexercise.data

import com.erdees.farmdataexercise.coreUtils.Constants.ERROR_MESSAGE
import com.erdees.farmdataexercise.domain.model.FarmDataUser
import com.erdees.farmdataexercise.domain.repository.AuthRepository
import com.erdees.farmdataexercise.feature_auth.domain.util.Registration
import com.erdees.farmdataexercise.model.Response
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@ExperimentalCoroutinesApi
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    @Named("userReference") private val usersReference: CollectionReference,
    ) : AuthRepository {

    override fun isUserAuthenticated() = auth.currentUser != null

    override fun getCurrentUserId(): String? {
       return auth.currentUser?.uid
    }


    override suspend fun getCurrentUserDocument(): Flow<Response<FarmDataUser>> = callbackFlow {
        if(!isUserAuthenticated()){
            trySend(Response.Error("NO UID!"))
            awaitClose()
        }
        else {
            val userId = auth.currentUser!!.uid
            val query = usersReference.document(userId)
            val snapShotListener = query.addSnapshotListener { snapshot, error ->
                val response = if (snapshot != null) {
                    val farmDataUser = snapshot.toObject(FarmDataUser::class.java)
                    Response.Success(farmDataUser!!)
                } else Response.Error(error?.message ?: error.toString())
                trySend(response).isSuccess
            }

            awaitClose {
                snapShotListener.remove()
            }
        }
    }

    override suspend fun signUpWithEmail(registration: Registration): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)
            auth.createUserWithEmailAndPassword(registration.mail,registration.password).await()
            emit(Response.Success(true))
            createUserDocument(registration)?.await()
            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }
    override fun createUserDocument(registration: Registration) : Task<Void>?{
        val farmDataUser = FarmDataUser(
            registration.firstName, registration.lastName, registration.mail,
            listOf()
        )
        return auth.currentUser?.uid?.let { usersReference.document(it).set(farmDataUser) }
    }


    override suspend fun signInWithEmail(email: String, password: String): Flow<Response<Boolean>> =
        flow {
            try {
                emit(Response.Loading)
                auth.signInWithEmailAndPassword(email, password).await()
                emit(Response.Success(true))

            } catch (e: Exception) {
                emit(Response.Error(e.message ?: ERROR_MESSAGE))
            }

        }

    override suspend fun signOut(): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)
            auth.apply {
                this.signOut()
                emit(Response.Success(true))
            }
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: ERROR_MESSAGE))
        }
    }

}