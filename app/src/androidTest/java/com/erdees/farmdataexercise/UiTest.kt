package com.erdees.farmdataexercise

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.erdees.farmdataexercise.mainActivity.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class UiTest {

    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule val hiltRule = HiltAndroidRule(this)

    lateinit var navController : TestNavHostController


    @Before
    fun setup() {
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun myTest() {
        composeTestRule.setContent {
             Navigation()
            composeTestRule.onNodeWithText(stringResource(id = R.string.sign_in))
        }
    }
}


