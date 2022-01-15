package com.erdees.farmdataexercise.feature_auth.presentation.profile

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.erdees.farmdataexercise.FakeConstants.fakeFarmDataUser
import com.erdees.farmdataexercise.coreUtils.Constants
import com.erdees.farmdataexercise.coreUtils.TestTags
import com.erdees.farmdataexercise.coreUtils.utils.Screen
import com.erdees.farmdataexercise.data.FakeAuthRepository
import com.erdees.farmdataexercise.di.AppModule
import com.erdees.farmdataexercise.feature_FarmData.presentation.addFarmData.AddFarmScreen
import com.erdees.farmdataexercise.feature_FarmData.presentation.detailedFarmDataGraphScreen.DetailedFarmDataGraphScreen
import com.erdees.farmdataexercise.feature_FarmData.presentation.farmDataScreen.FarmDataScreen
import com.erdees.farmdataexercise.feature_FarmData.presentation.selectFarm.SelectFarmScreen
import com.erdees.farmdataexercise.feature_FarmData.presentation.selectFarmData.SelectFarmDataScreen
import com.erdees.farmdataexercise.feature_auth.presentation.signIn.SignInScreen
import com.erdees.farmdataexercise.feature_auth.presentation.signUp.SignUpScreen
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
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltAndroidTest
@UninstallModules(AppModule::class)
class ProfileScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @InternalCoroutinesApi
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var fakeAuthRepository: FakeAuthRepository

    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    @InternalCoroutinesApi
    @Before
    fun setUp() {
        hiltRule.inject()
        fakeAuthRepository.isUserAuth = true // BECAUSE IN PROVIDED FAKE AUTH REPOSITORY USER IS LOGGED OUT

        composeTestRule.setContent {
            FarmDataExerciseTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.ProfileScreen.route) {
                    composable(route = Screen.SignUpScreen.route){
                        SignUpScreen(navController = navController)
                    }
                    composable(route = Screen.SelectFarmScreen.route){
                        SelectFarmScreen(navController = navController)
                    }
                    composable(route = Screen.SignInScreen.route){
                        SignInScreen(navController = navController)
                    }
                    composable(route = Screen.AddFarmDataScreen.route + "?${Constants.LOCATION_DOC_ID}={${Constants.LOCATION_DOC_ID}}&${Constants.LOCATION_NAME}={${Constants.LOCATION_NAME}}&${Constants.FARM_IMAGE_URL}={${Constants.FARM_IMAGE_URL}}",arguments = listOf(
                        navArgument(Constants.LOCATION_DOC_ID){
                            type = NavType.StringType
                            nullable = true
                        },
                        navArgument(Constants.LOCATION_NAME){
                            type = NavType.StringType
                            nullable = true
                        },
                        navArgument(Constants.FARM_IMAGE_URL){
                            type = NavType.StringType
                            defaultValue = Constants.FARM_DEFAULT_IMAGE
                        }
                    )) {
                        AddFarmScreen(navController = navController)
                    }
                    composable(route = Screen.SelectFarmDataScreen.route + "?${Constants.LOCATION_DOC_ID}={${Constants.LOCATION_DOC_ID}}&${Constants.LOCATION_NAME}={${Constants.LOCATION_NAME}}&${Constants.FARM_IMAGE_URL}={${Constants.FARM_IMAGE_URL}}",arguments = listOf(
                        navArgument(Constants.LOCATION_DOC_ID){
                            type = NavType.StringType
                            nullable = true
                        },
                        navArgument(Constants.LOCATION_NAME){
                            type = NavType.StringType
                            nullable = true
                        },
                        navArgument(Constants.FARM_IMAGE_URL){
                            type = NavType.StringType
                            defaultValue = Constants.FARM_DEFAULT_IMAGE
                        }
                    )) {
                        SelectFarmDataScreen(navController = navController)
                    }
                    composable(route = Screen.ProfileScreen.route){
                        ProfileScreen(navController = navController)
                    }
                    composable(route = Screen.FarmDataScreen.route + "/{${Constants.LOCATION_DOC_ID}}/{${Constants.LOCATION_NAME}}/{${Constants.SENSOR_TYPE}}/{${Constants.RANGE_FIRST}}/{${Constants.RANGE_SECOND}}/{${Constants.SENSOR_NAME}}", arguments = listOf(
                        navArgument(Constants.LOCATION_DOC_ID) {
                            type = NavType.StringType
                            nullable = false
                        }, navArgument(Constants.LOCATION_NAME) {
                            type = NavType.StringType
                            nullable = false
                        }, navArgument(Constants.SENSOR_TYPE) {
                            type = NavType.StringType
                            nullable = false
                        }, navArgument(Constants.RANGE_FIRST) {
                            type = NavType.StringType
                            nullable = false
                        }, navArgument(Constants.RANGE_SECOND) {
                            type = NavType.StringType
                            nullable = false
                        }, navArgument(Constants.SENSOR_NAME) {
                            type = NavType.StringType
                            nullable = false
                        })
                    ) {
                        FarmDataScreen(navController = navController)
                    }
                    composable(route = Screen.DetailedFarmDataGraphScreen.route + "/{${Constants.LOCATION_NAME}}/{${Constants.SENSOR_NAME}}", arguments = listOf(
                        navArgument(Constants.LOCATION_NAME){
                            type = NavType.StringType
                            nullable = false
                        }, navArgument(Constants.SENSOR_NAME){
                            type = NavType.StringType
                            nullable = false
                        }
                    )
                    ){ DetailedFarmDataGraphScreen() }
                }
            }
        }
    }

    @InternalCoroutinesApi
    @Test
    fun profileScreen_ShouldLoadFakeUserData(){
        composeTestRule.onNodeWithTag(TestTags.PROFILE_SCREEN_SIGN_OUT_BTN).assertIsDisplayed()
        composeTestRule.onNodeWithText("Hello!").assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeFarmDataUser.email).assertIsDisplayed()
        composeTestRule.onNodeWithText("${fakeFarmDataUser.firstName} ${fakeFarmDataUser.lastName}").assertIsDisplayed()
    }


    @InternalCoroutinesApi
    @Test
    fun signOutButton_shouldNavigate_toSignInScreen(){
        composeTestRule.onNodeWithTag(TestTags.PROFILE_SCREEN_SIGN_OUT_BTN).performClick()
        composeTestRule.onNodeWithTag(TestTags.SIGN_IN_TAG).assertIsDisplayed().printToLog(TestTags.SIGN_IN_TAG)
    }

}