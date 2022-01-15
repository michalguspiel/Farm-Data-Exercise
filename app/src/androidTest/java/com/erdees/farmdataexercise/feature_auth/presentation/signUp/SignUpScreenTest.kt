package com.erdees.farmdataexercise.feature_auth.presentation.signUp

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.erdees.farmdataexercise.coreUtils.TestTags
import com.erdees.farmdataexercise.coreUtils.utils.Screen
import com.erdees.farmdataexercise.di.AppModule
import com.erdees.farmdataexercise.feature_auth.presentation.signIn.SignInScreen
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
class SignUpScreenTest {

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
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.SignUpScreen.route
                ) {
                    composable(route = Screen.SignUpScreen.route) {
                        SignUpScreen(navController = navController)
                    }
                    composable(route = Screen.SignInScreen.route){
                        SignInScreen(navController = navController)
                    }
                }
            }
        }
    }

    @InternalCoroutinesApi
    @Test
    fun testIfSignInUpVisible() {
        composeTestRule.onNodeWithTag(TestTags.SIGN_IN_TAG).assertDoesNotExist()
        composeTestRule.onNodeWithTag(TestTags.SIGN_UP_TAG).assertIsDisplayed()
            .printToLog(TestTags.SIGN_UP_TAG)
    }


    @InternalCoroutinesApi
    @Test
    fun testIfClickingSignUp_WillShowAlertDialog() {
        composeTestRule.onNodeWithTag(TestTags.SIGN_UP_BUTTON_TAG).performClick()
        composeTestRule.onNodeWithTag(TestTags.ALERT_DIALOG_TAG).assertIsDisplayed()
    }

    @InternalCoroutinesApi
    @Test
    fun testIfClickingSignIn_ShouldNavigateToSignInScreen() {
        composeTestRule.onNodeWithTag(TestTags.NAVIGATE_FROM_SIGN_UP_TO_SIGN_IN_BUTTON)
            .performClick()
        composeTestRule.onNodeWithTag(TestTags.SIGN_IN_TAG).assertIsDisplayed()
    }

}