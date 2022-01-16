package com.erdees.farmdataexercise.feature_FarmData.domain.util

object Util {
    fun isNumber(string: String): Boolean {
        return when {
            string == "-" -> true
            string == "" -> true
            string.matches(".*[a-zA-Z]+.*".toRegex()) -> false
            else -> {
                when (string.toDoubleOrNull()) {
                    null -> false
                    else -> true
                }
            }
        }
    }
}