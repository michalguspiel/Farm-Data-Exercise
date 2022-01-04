package com.erdees.farmdataexercise.feature_auth.domain.util

import android.text.TextUtils

class Registration(val password: String, private val confirmPassword: String, val mail: String, val firstName: String, val lastName: String) {

    private fun passwordTooShort(password: String): Boolean {
        return password.length < 7
    }

    private fun passwordsAreSame(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    private fun passwordContainsDigit(password: String): Boolean {
        return password.any { it.isDigit() }
    }

    private fun passwordIsOnlyDigits(password: String): Boolean {
        return password.all { it.isDigit() }
    }

    fun isLegit(): Boolean {
        return (passwordContainsDigit(password) &&
                !passwordIsOnlyDigits(password) &&
                !passwordTooShort(password)&&
                mail.isEmailValid() &&
                firstName.isNotBlank() &&
                lastName.isNotBlank() &&
                passwordsAreSame(password, confirmPassword))
    }

    val errorMessage: String =
        when {
            !this.mail.isEmailValid() -> "Provide valid email!"
            !passwordsAreSame(password, confirmPassword) -> "Passwords are not same!"
            passwordTooShort(password) -> "Password too short!"
            !passwordContainsDigit(password) -> "Password has to contain at least one digit!"
            passwordIsOnlyDigits(password) -> "Password can't contain digits only!"
            firstName.isBlank() -> "First name required!"
            lastName.isBlank() -> "Last name required!"
            else -> "No error"
        }

    private fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
}