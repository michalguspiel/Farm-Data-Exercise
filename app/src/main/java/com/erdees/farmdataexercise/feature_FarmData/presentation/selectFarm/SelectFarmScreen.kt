package com.erdees.farmdataexercise.feature_FarmData.presentation.selectFarm

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdees.farmdataexercise.R
import com.erdees.farmdataexercise.coreUtils.Constants.FARM_IMAGE_URL
import com.erdees.farmdataexercise.coreUtils.Constants.LOCATION_DOC_ID
import com.erdees.farmdataexercise.coreUtils.Constants.LOCATION_NAME
import com.erdees.farmdataexercise.coreUtils.components.MyButton
import com.erdees.farmdataexercise.coreUtils.components.MyTopAppBar
import com.erdees.farmdataexercise.coreUtils.components.ProgressBar
import com.erdees.farmdataexercise.coreUtils.utils.Screen
import com.erdees.farmdataexercise.coreUtils.utils.Util.toDp
import com.erdees.farmdataexercise.feature_FarmData.domain.model.FarmInformation
import com.erdees.farmdataexercise.feature_FarmData.presentation.components.AddFarmDialog
import com.erdees.farmdataexercise.feature_FarmData.presentation.components.Toast
import com.erdees.farmdataexercise.feature_FarmData.presentation.components.model.CornerStatus
import com.erdees.farmdataexercise.feature_FarmData.presentation.components.model.HorizontalCorner
import com.erdees.farmdataexercise.feature_FarmData.presentation.components.model.VerticalCorner
import com.erdees.farmdataexercise.feature_FarmData.presentation.selectFarm.utils.WindowAdapter
import com.erdees.farmdataexercise.feature_FarmData.presentation.selectFarm.utils.rememberMapViewWithLifecycle
import com.erdees.farmdataexercise.model.Response
import com.erdees.farmdataexercise.ui.theme.*
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.model.*
import com.google.firebase.firestore.GeoPoint
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@Composable
fun SelectFarmScreen(
    viewModel: SelectFarmScreenViewModel = hiltViewModel(),
    navController: NavController
) {

    val horizontalCornerStatus: MutableState<HorizontalCorner> = remember {
        mutableStateOf(HorizontalCorner.StartCorner)
    }

    val verticalCornerStatus: MutableState<VerticalCorner> = remember {
        mutableStateOf(VerticalCorner.TopCorner)
    }

    var isMapCentered = remember {
        false
    }

    var clickPosAsDpOffset by remember {
        mutableStateOf(Pair(0f, 0f))
    }

    var buttonHeight by remember {
        mutableStateOf(0f)
    }

    var buttonWidth by remember {
        mutableStateOf(0f)
    }

    var totalScreenWidth by remember {
        mutableStateOf(0f)
    }

    var scaffoldHeight by remember {
        mutableStateOf(0f)
    }

    val mapView = rememberMapViewWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.getLocalFarmsInformation()
        viewModel.clearSelectedFarm()
    }

    val context = LocalContext.current
    val density = LocalDensity.current
    val padding = LocalSpacing.current.large.value

    Scaffold(topBar = {
        MyTopAppBar(
            screen = Screen.SelectFarmScreen,
            navController = navController,
            modifier = Modifier.onGloballyPositioned {
                scaffoldHeight = it.size.height.toFloat().toDp(density)
            }
        )
    }) {
        if (viewModel.isOpenDialogState.value) {
            AddFarmDialog()
        }

        when (viewModel.farmAdditionState.value) {
            is Response.Loading -> ProgressBar()
            is Response.Success -> {
                Toast(stringResource(R.string.farm_added_successfully))
            }
            is Response.Error -> {
                Toast(stringResource(R.string.farm_addition_error_message))
            }
            else -> {
            }
        }
        when (val farmsInformationResponse = viewModel.farmsInformationState.value) {
            is Response.Loading -> ProgressBar()
            is Response.Success -> Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .background(Color.White)
                    .onGloballyPositioned {
                        totalScreenWidth = it.size.width
                            .toFloat()
                            .toDp(density)
                    }
            ) {
                AndroidView({ mapView }) { mapView ->
                    CoroutineScope(Dispatchers.Main).launch {

                        val adapter = WindowAdapter(context)
                        val map = mapView.awaitMap()

                        if (viewModel.farmAdditionState.value is Response.Success) {
                            viewModel.getLocalFarmsInformation()
                            viewModel.resetFarmAdditionResponse()
                        }
                        map.uiSettings.isZoomControlsEnabled = true
                        map.setInfoWindowAdapter(
                            adapter
                        )


                        map.setOnMarkerClickListener { marker ->
                            viewModel.selectFarm(marker)
                            marker.showInfoWindow()
                            map.moveCamera(CameraUpdateFactory.newLatLng(marker.position))
                            true
                        }

                        if (viewModel.isUserAuthenticated) map.setOnMapLongClickListener { latLng ->
                            viewModel.isAddFarmButtonShownState.value = true
                            viewModel.clickedLatLngState.value = latLng
                            val screenLocation = map.projection.toScreenLocation(latLng)
                            clickPosAsDpOffset = Pair(
                                screenLocation.x.toFloat().toDp(density),
                                screenLocation.y.toFloat().toDp(density)
                            )
                        }

                        map.setOnMapClickListener {
                            unselectMarker(viewModel)
                        }

                        map.setOnInfoWindowCloseListener {
                            viewModel.clearSelectedFarm()
                            adapter.drawable = null
                        }


                        farmsInformationResponse.data.forEach {
                            val marker = map.markFarmOnTheMap(
                                it.locationName,
                                LatLng(it.geoPoint.latitude, it.geoPoint.longitude),
                                it.farmImageUrl,
                                context
                            )
                            marker.tag = it.docId
                            viewModel.addPairToMap(Pair(marker.tag.toString(), it))
                        }

                        if (!isMapCentered) {
                            map.centerMapAtHelsinki()
                            isMapCentered = true
                        }
                    }
                }

                if (viewModel.isAddFarmButtonShownState.value) {
                    Button(
                        onClick = {
                            viewModel.isOpenDialogState.value = true
                            viewModel.isAddFarmButtonShownState.value = false
                        },colors = ButtonDefaults.buttonColors(backgroundColor = Yellow100),
                        modifier = Modifier
                            .onGloballyPositioned {
                                buttonHeight = it.size.height
                                    .toFloat()
                                    .toDp(density)
                                buttonWidth = it.size.width
                                    .toFloat()
                                    .toDp(density)
                            }
                            .absoluteOffset(
                                calculateXOffset(
                                    offset = clickPosAsDpOffset.first,
                                    buttonWidth = buttonWidth,
                                    screenWidth = totalScreenWidth,
                                    padding = padding,
                                    cornerStatus = horizontalCornerStatus
                                ).dp,
                                calculateYOffset(
                                    offset = clickPosAsDpOffset.second,
                                    buttonHeight = buttonHeight,
                                    scaffoldHeight = scaffoldHeight,
                                    padding = padding,
                                    cornerStatus = verticalCornerStatus
                                ).dp
                            ),
                        shape = CornerStatus(
                            horizontalCorner = horizontalCornerStatus.value,
                            verticalCorner = verticalCornerStatus.value
                        ).provideShape(rounding = LocalCorner.current.large)
                    ) {
                        Text(
                            text = stringResource(R.string.add_farm),
                            modifier = Modifier.padding(LocalSpacing.current.small)
                        )
                    }
                }

                androidx.compose.animation.AnimatedVisibility(
                    viewModel.selectedFarmState.value != null,
                    modifier = Modifier.align(
                        BottomCenter
                    ),
                    enter = slideInVertically(initialOffsetY = { fullHeight -> fullHeight / 2 }),
                    exit = slideOutVertically(targetOffsetY = { fullHeight -> fullHeight / 2 })
                ) {
                    BottomCard(
                        viewModel.selectedFarmState.value, Modifier.align(
                            BottomCenter
                        ), navController,viewModel.currentUserId
                    )
                }
            }
            is Response.Error -> Toast(farmsInformationResponse.message)
        }

    }
}

fun unselectMarker(viewModel: SelectFarmScreenViewModel){
    viewModel.isAddFarmButtonShownState.value = false
    viewModel.clearSelectedFarm()
}


fun setHorizontalCorner(
    horizontalCorner: HorizontalCorner,
    cornerStatus: MutableState<HorizontalCorner>
) {
    cornerStatus.value = horizontalCorner
}

@Composable
fun calculateXOffset(
    offset: Float,
    buttonWidth: Float,
    screenWidth: Float,
    padding: Float,
    cornerStatus: MutableState<HorizontalCorner>
): Float {
    return when {
        offset + buttonWidth + padding > screenWidth -> {
            setHorizontalCorner(
                horizontalCorner = HorizontalCorner.EndCorner,
                cornerStatus = cornerStatus
            )
            offset - buttonWidth - padding
        }
        else -> {
            setHorizontalCorner(
                horizontalCorner = HorizontalCorner.StartCorner,
                cornerStatus = cornerStatus
            )
            offset + padding
        }
    }
}

fun setVerticalCorner(verticalCorner: VerticalCorner, cornerStatus: MutableState<VerticalCorner>) {
    cornerStatus.value = verticalCorner
}

@Composable
fun calculateYOffset(
    offset: Float,
    buttonHeight: Float,
    scaffoldHeight: Float,
    padding: Float,
    cornerStatus: MutableState<VerticalCorner>
): Float {
    return when {
        offset - padding - buttonHeight < scaffoldHeight -> {
            setVerticalCorner(VerticalCorner.TopCorner, cornerStatus)
            offset + padding
        }
        else -> {
            setVerticalCorner(VerticalCorner.BottomCorner, cornerStatus)
            offset - buttonHeight - padding
        }
    }
}

@Composable
fun BottomCard(
    farmInformation: FarmInformation?,
    modifier: Modifier,
    navController: NavController,
    currentUserId : String?
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = LocalSpacing.current.xLarge),
        shape = RoundedCornerShape(
            LocalCorner.current.xLarge,
            LocalCorner.current.xLarge,
            0.dp,
            0.dp
        ), elevation = LocalElevation.current.default
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = LocalSpacing.current.default),
            horizontalAlignment = CenterHorizontally
        ) {
            farmInformation?.locationName?.let {
                Text(
                    text = it,
                    style = Typography.h4,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(Modifier.height(LocalSpacing.current.xxSmall))

            Column(modifier = Modifier.width(IntrinsicSize.Max),
                horizontalAlignment = CenterHorizontally) {
                MyButton(onClick = {
                    navController.navigate(
                        Screen.SelectFarmDataScreen.route +
                                "?$LOCATION_DOC_ID=${farmInformation?.docId}&$LOCATION_NAME=${farmInformation?.locationName}&$FARM_IMAGE_URL=${farmInformation?.farmImageUrl}"
                    )
                }, text = stringResource(R.string.select_farm),modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(LocalSpacing.current.default))

                if(currentUserId != null && farmInformation != null && currentUserId == farmInformation.farmOwnerId){
                    MyButton(onClick = {
                        navController.navigate(
                            Screen.AddFarmDataScreen.route +
                                    "?$LOCATION_DOC_ID=${farmInformation.docId}&$LOCATION_NAME=${farmInformation.locationName}&$FARM_IMAGE_URL=${farmInformation.farmImageUrl}"
                        )
                    },modifier = Modifier.fillMaxWidth(),text = stringResource(R.string.add_farm_data))
                    Spacer(modifier = Modifier.height(LocalSpacing.current.default))
                }
            }
            }
    }
}

@Composable
@Preview
fun BottomCardPreview() {
    BottomCard(
        farmInformation = FarmInformation(
            "Michal's farm",
            GeoPoint(20.0, 30.0),
            "DocID",
            "FarmImage"
        ), Modifier, navController = NavController(LocalContext.current),null
    )
}


fun GoogleMap.markFarmOnTheMap(
    locationName: String,
    latLng: LatLng,
    farmImageUrl: String,
    context: Context
): Marker {
    val markerOption = MarkerOptions()
        .title(locationName)
        .position(latLng)
        .snippet(farmImageUrl)
        .icon(bitmapDescriptorFromVector(context, R.drawable.ic_baseline_room_48))
    return this.addMarker(markerOption)
}

fun GoogleMap.centerMapAtHelsinki() {
    Log.i("Select farm data screen", "Centered at helsinki!")
    val destination = LatLng(60.16952, 24.44945)
    this.moveCamera(CameraUpdateFactory.newLatLngZoom(destination, 6f))
}

private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
    return ContextCompat.getDrawable(context, vectorResId)?.run {
        setBounds(0, 0, intrinsicWidth, intrinsicHeight)
        val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
        draw(Canvas(bitmap))
        BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}

