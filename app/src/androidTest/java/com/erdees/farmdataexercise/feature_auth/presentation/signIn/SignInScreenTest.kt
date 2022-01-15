package com.erdees.farmdataexercise.feature_auth.presentation.signIn

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import com.erdees.farmdataexercise.Navigation
import com.erdees.farmdataexercise.coreUtils.TestTags
import com.erdees.farmdataexercise.coreUtils.TestTags.SIGN_IN_TAG
import com.erdees.farmdataexercise.di.AppModule
import com.erdees.farmdataexercise.mainActivity.MainActivity
import com.erdees.farmdataexercise.ui.theme.FarmDataExerciseTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltAndroidTest
@UninstallModules(AppModule::class)
class SignInScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @InternalCoroutinesApi
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()


    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    @InternalCoroutinesApi
    @Before
    fun setUp() {
        hiltRule.inject()
        composeTestRule.setContent {
            FarmDataExerciseTheme {
                Navigation()
            }
        }
    }

    @InternalCoroutinesApi
    @Test
    fun testIfSignInIsVisible() {
        composeTestRule.onNodeWithTag(SIGN_IN_TAG).assertIsDisplayed().printToLog(SIGN_IN_TAG)
        composeTestRule.onNodeWithTag(TestTags.SIGN_UP_TAG).assertDoesNotExist()
    }

    @InternalCoroutinesApi
    @Test
    fun testIfPressingSignInWithNoInput_WillShowAlertDialog(){
        composeTestRule.onNodeWithTag(TestTags.SIGN_IN_BUTTON_TAG).performClick()
        composeTestRule.onNodeWithTag(TestTags.ALERT_DIALOG_TAG).assertIsDisplayed()
    }

    @InternalCoroutinesApi
    @Test
    fun testIfClickingNavigateToSignUp_WillNavigateProperly(){
        composeTestRule.onNodeWithTag(TestTags.NAVIGATE_FROM_SIGN_IN_TO_SIGN_UP_BTN).performClick()
        composeTestRule.onNodeWithTag(TestTags.SIGN_UP_TAG).assertIsDisplayed()

    }

}