package com.erdees.farmdataexercise.feature_auth

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.erdees.farmdataexercise.FakeConstants
import com.erdees.farmdataexercise.Navigation
import com.erdees.farmdataexercise.coreUtils.TestTags
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
class AuthEndToEndTest {

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
    fun signIn_TEST(){
        /**PROVIDE USER DATA INPUT*/
        composeTestRule.onNodeWithTag(TestTags.SIGN_IN_EMAIL).performTextInput(FakeConstants.FAKE_EMAIL_INPUT)
        composeTestRule.onNodeWithTag(TestTags.SIGN_IN_PASSWORD).performTextInput(FakeConstants.FAKE_PASSWORD_INPUT)
        /**CLICK SIGN IN*/
        composeTestRule.onNodeWithTag(TestTags.SIGN_IN_BUTTON_TAG).performClick()
        /**CHECK IF USER IS LOGGED IN AND NAVIGATED TO PROFILE SCREEN*/
        composeTestRule.onNodeWithText("Hello!").assertIsDisplayed()
    }

    @InternalCoroutinesApi
    @Test
    fun signIn_Then_SignOut_Test(){
        /**PROVIDE USER DATA INPUT*/
        composeTestRule.onNodeWithTag(TestTags.SIGN_IN_EMAIL).performTextInput(FakeConstants.FAKE_EMAIL_INPUT)
        composeTestRule.onNodeWithTag(TestTags.SIGN_IN_PASSWORD).performTextInput(FakeConstants.FAKE_PASSWORD_INPUT)
        /**CLICK SIGN IN*/
        composeTestRule.onNodeWithTag(TestTags.SIGN_IN_BUTTON_TAG).performClick()
        /**CHECK IF USER IS LOGGED IN AND NAVIGATED TO PROFILE SCREEN*/
        composeTestRule.onNodeWithText("Hello!").assertIsDisplayed()
        /**THEN SIGN OUT*/
        composeTestRule.onNodeWithTag(TestTags.PROFILE_SCREEN_SIGN_OUT_BTN).performClick()
        /**THEN CHECK IF USER IS NAVIGATED BACK TO SING IN SCREEN*/
        composeTestRule.onNodeWithTag(TestTags.SIGN_IN_TAG).assertIsDisplayed()
    }

    @InternalCoroutinesApi
    @Test
    fun signUp_Test(){
        /**NAVIGATE TO SIGN UP*/
        composeTestRule.onNodeWithTag(TestTags.NAVIGATE_FROM_SIGN_IN_TO_SIGN_UP_BTN).performClick()
        /**PROVIDE REGISTRATION INPUT*/
        composeTestRule.onNodeWithTag(TestTags.SIGN_UP_EMAIL).performTextInput(FakeConstants.FAKE_EMAIL_INPUT)
        composeTestRule.onNodeWithTag(TestTags.SIGN_UP_PASSWORD).performTextInput(FakeConstants.FAKE_PASSWORD_INPUT)
        composeTestRule.onNodeWithTag(TestTags.SIGN_UP_CONFIRMPASSWORD).performTextInput(
            FakeConstants.FAKE_PASSWORD_INPUT
        )
        composeTestRule.onNodeWithTag(TestTags.SIGN_UP_NAME).performTextInput(FakeConstants.fakeFarmDataUser.firstName)
        composeTestRule.onNodeWithTag(TestTags.SIGN_UP_LASTNAME).performTextInput(FakeConstants.fakeFarmDataUser.lastName)
        /**TRY TO SIGN UP*/
        composeTestRule.onNodeWithTag(TestTags.SIGN_UP_BUTTON_TAG).performClick()
        /**CHECK IF REGISTERED AND NAVIGATED TO PROFILE SCREEN*/
        composeTestRule.onNodeWithTag(TestTags.PROFILE_SCREEN_SIGN_OUT_BTN).assertIsDisplayed()
    }

}