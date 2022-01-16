package com.erdees.farmdataexercise

import com.erdees.farmdataexercise.feature_FarmData.domain.util.Format
import junit.framework.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate
import java.util.*

class TimeFormatTest {


    val date = LocalDate.of(2022,1,10)

    @Test
    fun `formatting date as start of the day should return 2022-01-10 00 00 00`(){
      val formattedDate =  Format.formatDateAsStartOfDay(date)
        assertEquals("2022-01-10T00:00:00Z",formattedDate)
    }

    @Test
    fun `formatting date as the end of the day should return 2022-01-10 23 59 59`(){
        val formattedDate = Format.formatDateAsEndOfDay(date)
        assertEquals("2022-01-10T23:59:59Z",formattedDate)
    }


    @Test
    fun `when in UTC +2 2022 01 10T00 00 00Z  and formatting to app format time should be 02 00 00`(){
        val time = "2022-01-10T00:00:00Z"
        val formattedTime = Format.formatISO8601String(time, TimeZone.getTimeZone("Europe/Helsinki"))
        assertEquals("2022-01-10 02:00:00",formattedTime)
    }

    @Test
    fun `when in UTC +1 for instance Poland datetime with hour 22 00 00Z should be 23 00 00 after formatting`(){
        val time = "2022-01-10T22:00:00Z"
        val formattedTime = Format.formatISO8601String(time,TimeZone.getTimeZone("Europe/Warsaw"))
        assertEquals("2022-01-10 23:00:00",formattedTime)
    }

    @Test
    fun `when formatting to add date with time 22 00 00 with device set in finnish time after formatting result should be "2022 01 10T22 00 00+02" `(){
        val date = LocalDate.of(2022,1,10)
        val hour = 22
        val minutes = 0
        val formattedDateTime = Format.formatDateAndTimeToISO8601(date,hour,minutes,TimeZone.getTimeZone("Europe/Helsinki"))
        assertEquals("2022-01-10T22:00:00+02",formattedDateTime)
    }

    @Test
    fun `when formatting UTC +2 time to presentable time on device with finnish time zone time should stay same`(){
        val dateTime = "2022-01-10T22:00:00+02"
        val formattedTime = Format.formatISO8601String(dateTime,TimeZone.getTimeZone("Europe/Helsinki"))
        assertEquals("2022-01-10 22:00:00",formattedTime)
    }

    @Test
    fun `when formatting UTC +0 time to presentable time on device London time zone time should be -2`(){
        val dateTime = "2022-01-10T22:00:00+02"
        val formattedTime = Format.formatISO8601String(dateTime,TimeZone.getTimeZone("Europe/London"))
        assertEquals("2022-01-10 20:00:00",formattedTime)
    }



}