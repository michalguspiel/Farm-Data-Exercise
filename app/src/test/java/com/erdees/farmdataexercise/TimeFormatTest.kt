package com.erdees.farmdataexercise

import com.erdees.farmdataexercise.feature_FarmData.domain.util.Format
import junit.framework.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

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

}