package com.erdees.farmdataexercise.feature_auth.domain.use_case

data class UseCases(
    val isUserAuthenticated: IsUserAuthenticated,
    val signUpWithEmail: SignUpWithEmail,
    val signInWithEmail: SignInWithEmail,
    val signOut: SignOut,
    val getCurrentUserDocument: GetCurrentUserDocument
)

