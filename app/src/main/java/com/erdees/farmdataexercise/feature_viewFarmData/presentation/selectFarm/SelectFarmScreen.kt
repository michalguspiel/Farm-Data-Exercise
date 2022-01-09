package com.erdees.farmdataexercise.feature_viewFarmData.presentation.selectFarm

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
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
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
import com.erdees.farmdataexercise.coreUtils.utils.Screen
import com.erdees.farmdataexercise.feature_viewFarmData.domain.model.FarmInformation
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.components.ProgressBar
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.components.Toast
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.selectFarm.utils.WindowAdapter
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.selectFarm.utils.rememberMapViewWithLifecycle
import com.erdees.farmdataexercise.model.Response
import com.erdees.farmdataexercise.ui.theme.Typography
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.model.*
import com.google.firebase.firestore.GeoPoint
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * TODO : MAKE LAYOUT, MAP NEEDS TO BE INSIDE MAIN LAYOUT OF THIS SCREEN
 * TODO : SEARCH BAR
 * */
@ExperimentalAnimationApi
@Composable
fun SelectFarmScreen(
    viewModel: SelectFarmScreenViewModel = hiltViewModel(),
    navController: NavController
) {

    var isMapCentered = remember {
        false
    }

    val density = LocalDensity.current

    val mapView = rememberMapViewWithLifecycle()
    viewModel.getLocalFarmsInformation()

    val context = LocalContext.current

    Scaffold(topBar = { MyTopAppBar(screen = "Select farm", navController = navController) }) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(Color.White)
        ) {

            when (val farmsInformationResponse = viewModel.farmsInformationState.value) {
                is Response.Loading -> ProgressBar()
                is Response.Success -> Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    AndroidView({ mapView }) { mapView ->
                        CoroutineScope(Dispatchers.Main).launch {
                            val adapter = WindowAdapter(context)
                            val map = mapView.awaitMap()

                            map.setInfoWindowAdapter(
                                adapter
                            )
                            map.setOnMarkerClickListener { marker ->
                                viewModel.selectFarm(marker)
                                Log.i("Select farm screen", "${marker.position}")
                                marker.showInfoWindow()
                                map.moveCamera(CameraUpdateFactory.newLatLng(marker.position))
                                Log.i("Select farm screen", "Click! ${marker.title}")
                                true
                            }

                            map.setOnInfoWindowCloseListener {
                                viewModel.clearSelectedFarm()
                                adapter.drawable = null
                            }
                            map.uiSettings.isZoomControlsEnabled = true

                            farmsInformationResponse.data.forEach {
                                val marker = map.markFarmOnTheMap(
                                    it.locationName,
                                    LatLng(it.geoPoint.latitude, it.geoPoint.longitude),
                                    it.farmImageUrl,
                                    context
                                )
                                viewModel.markerMap.add(Pair(marker, it))
                            }
                            if (!isMapCentered) {
                                map.centerMapAtHelsinki()
                                isMapCentered = true
                            }
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
                            ), navController
                        )
                    }
                }
                is Response.Error -> Toast(farmsInformationResponse.message)
            }

        }
    }
}

@Composable
fun BottomCard(
    farmInformation: FarmInformation?,
    modifier: Modifier,
    navController: NavController
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        shape = RoundedCornerShape(18.dp, 18.dp, 0.dp, 0.dp), elevation = 8.dp
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalAlignment = CenterHorizontally
        ) {
            farmInformation?.locationName?.let {
                Text(
                    text = it,
                    style = Typography.h4,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(Modifier.height(2.dp))
            MyButton(onClick = {  navController.navigate(
                Screen.SelectFarmDataScreen.route +
                        "?$LOCATION_DOC_ID=${farmInformation?.docId}&$LOCATION_NAME=${farmInformation?.locationName}&$FARM_IMAGE_URL=${farmInformation?.farmImageUrl}"
            )}, text = "Select farm")
                Spacer(Modifier.height(2.dp))
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
        ), Modifier, navController = NavController(LocalContext.current)
    )
}

fun GoogleMap.markFarmOnTheMap(
    locationName: String,
    latLng: LatLng,
    farmImageUrl: String,
    context: Context
): Marker {
    Log.i("Select farm screen", farmImageUrl)
    val markerOption = MarkerOptions()
        .title(locationName)
        .position(latLng)
        .snippet(farmImageUrl)
        .icon(bitmapDescriptorFromVector(context, R.drawable.ic_farm_map_marker))
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

