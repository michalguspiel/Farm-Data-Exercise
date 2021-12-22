package com.erdees.farmdataexercise.feature_viewFarmData.domain.util

import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object Format {

    fun formatISO8601String(string: String): String {
        val desiredFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        val dateTime = OffsetDateTime.parse(string)
        return dateTime.format(desiredFormat)
    }

    fun formatDate(date: LocalDate) : String{
        val desiredFormat = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssX")
        return date.atStartOfDay().atOffset(ZoneOffset.UTC).format(desiredFormat)
    }

    fun formatToSeconds(string: String) :Long {
        val netDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        val date = OffsetDateTime.parse(string)
        return date.toEpochSecond()
    }

}