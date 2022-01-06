package com.erdees.farmdataexercise.feature_viewFarmData.presentation.selectFarm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdees.farmdataexercise.coreUtils.components.MyTopAppBar
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.components.ProgressBar
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.components.Toast
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.selectFarm.utils.rememberMapViewWithLifecycle
import com.erdees.farmdataexercise.model.Response
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SelectFarmScreen(
    viewModel: SelectFarmScreenViewModel = hiltViewModel(),
    navController: NavController
) {

    val mapView = rememberMapViewWithLifecycle()
    viewModel.getLocalFarmsInformation()

    Scaffold(topBar = { MyTopAppBar(screen = "Select farm", navController = navController) }) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(Color.White)
        ) {

            when (val farmsInformationResponse = viewModel.farmsInformationState.value) {
                is Response.Loading -> ProgressBar()
                is Response.Success -> AndroidView({ mapView }) { mapView ->
                    CoroutineScope(Dispatchers.Main).launch {
                        val map = mapView.awaitMap()
                        map.uiSettings.isZoomControlsEnabled = true

                        farmsInformationResponse.data.forEach {
                            val markerOptions = MarkerOptions()
                                .title(it.locationName)
                                .position(LatLng(it.geoPoint.latitude, it.geoPoint.longitude))
                            map.addMarker(markerOptions)
                        }

                        val destination = LatLng(60.16952, 24.44945)
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(destination, 6f))


                    }
                }
                is Response.Error -> Toast(farmsInformationResponse.message)
            }


        }
    }
}


