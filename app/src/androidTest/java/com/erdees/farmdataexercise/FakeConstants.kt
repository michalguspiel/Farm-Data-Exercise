package com.erdees.farmdataexercise

import com.erdees.farmdataexercise.domain.model.FarmDataUser

object FakeConstants {
    const val FAKE_OWNER_ID = "fake_ownerID"

    const val FAKE_LOCATION ="Fake location1"
    const val PH = "pH"

    const val FAKE_EMAIL_INPUT = "testEmail@gmail.com"
        const val FAKE_PASSWORD_INPUT = "testPassword123"

    const val FAKE_FARM_NAME_AND_MARKER_TITLE = "First fake farm"

    val fakeFarmDataUser = FarmDataUser("Fake","Faker", FAKE_EMAIL_INPUT, listOf())


}