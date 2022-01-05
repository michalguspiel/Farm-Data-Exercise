package com.erdees.farmdataexercise.feature_auth.presentation.signUp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdees.farmdataexercise.feature_auth.domain.use_case.UseCases
import com.erdees.farmdataexercise.feature_auth.domain.util.Registration
import com.erdees.farmdataexercise.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val useCases: UseCases,
) : ViewModel() {

    val isUserAuthenticated get() = useCases.isUserAuthenticated()

    private val _signUpState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val signUpState: State<Response<Boolean>> = _signUpState

    fun startSigningUpWithEmail(
        password: String,
        confirmPassword: String,
        email: String,
        firstName: String,
        lastName: String
    ) {
        val registration = Registration(password, confirmPassword, email, firstName, lastName)
        _signUpState.value = Response.Loading
        if (!registration.isLegit()) {
            _signUpState.value = Response.Error(registration.errorMessage)
        } else {
            signUpWithEmail(registration)
        }
    }

    private fun signUpWithEmail(registration: Registration) {
        viewModelScope.launch {
            useCases.signUpWithEmail(registration).collect { response ->
                _signUpState.value = response
            }
        }
    }
}
