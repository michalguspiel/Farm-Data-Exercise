package com.erdees.farmdataexercise

import com.erdees.farmdataexercise.feature_FarmData.domain.util.Util.isNumber
import org.junit.Assert.assertEquals
import org.junit.Test


class FarmDataUtilTest {

    @Test
    fun `when number is 100 function isNumber should return true`(){
        val number = "100"
        assertEquals(true,isNumber(number))
    }

    @Test
    fun `when number is 1dot0 function isNumber should return true`(){
        val number = "1.0"
        assertEquals(true,isNumber(number))
    }

    @Test
    fun `when number is 1dot function isNumber should return true`(){
        val number = "1."
        assertEquals(true,isNumber(number))
    }

    @Test
    fun `when number is dot1 function isNumber should return true`(){
        val number = ".1"
        assertEquals(true,isNumber(number))
    }

    @Test
    fun `when number is 1f isNumber should return false`(){
        val number = "1f"
        assertEquals(false, isNumber(number))
    }

    @Test
    fun `when number is 1d isNumber should return false`(){
        val number = "1d"
        assertEquals(false, isNumber(number))
    }

    @Test
    fun `should return true for empty string`(){
        val number =""
        assertEquals(true, isNumber(number))
    }
}