package com.erdees.farmdataexercise.feature_FarmData.domain.util

import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

object Format {

    fun formatISO8601String(string: String, tZone: TimeZone = TimeZone.getDefault()): String {
        val timeZone =  tZone.toZoneId()
        val desiredFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val dateTime = OffsetDateTime.parse(string)
        return dateTime.format(desiredFormat.withZone(timeZone))
    }

    fun formatDateAndTimeToISO8601(date: LocalDate, hour: Int,minutes: Int, tZone : TimeZone = TimeZone.getDefault()): String{
        val timeZone = tZone.toZoneId()
        val desiredFormat = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssX")
        val dateTime = date.atTime(hour,minutes)
        val dateTimeWithZone = dateTime.atZone(timeZone)
        return dateTimeWithZone.format(desiredFormat)
    }

    fun formatDateAsStartOfDay(date: LocalDate): String {
        val desiredFormat = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssX")
        return date.atStartOfDay().atOffset(ZoneOffset.UTC).format(desiredFormat)
    }

    fun formatDateAsEndOfDay(date: LocalDate) : String{
        val desiredFormat = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssX")
        return date.atTime(23,59,59).atOffset(ZoneOffset.UTC).format(desiredFormat)
    }

    fun formatTemperature(value: String): String {
        return "$value \u2103"
    }

    fun formatDateToYearMonthDay(string: String): String {
        val desiredFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = OffsetDateTime.parse(string)
        return date.format(desiredFormat)
    }

    fun formatToSeconds(string: String): Long {
        val date = OffsetDateTime.parse(string)
        return date.toEpochSecond()
    }

}