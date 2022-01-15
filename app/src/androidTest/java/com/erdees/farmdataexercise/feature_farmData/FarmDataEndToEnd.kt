package com.erdees.farmdataexercise.feature_farmData

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
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
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@HiltAndroidTest
@UninstallModules(AppModule::class)
@RunWith(AndroidJUnit4::class)
class FarmDataEndToEnd {

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
    fun whenContinueAnonymouslyIsClicked_MapView_ShouldBeVisible() {
        /**Navigate to select farm screen*/
        composeTestRule.onNodeWithTag(TestTags.CONTINUE_ANONYMOUSLY).performClick()
        /**Check if navigated properly*/
        composeTestRule.onNodeWithTag(TestTags.MAP_TAG).assertIsDisplayed()
    }
}



