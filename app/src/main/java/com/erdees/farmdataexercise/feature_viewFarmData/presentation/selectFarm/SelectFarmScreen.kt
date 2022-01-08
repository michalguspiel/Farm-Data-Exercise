package com.erdees.farmdataexercise.feature_viewFarmData.presentation.selectFarm

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import android.view.LayoutInflater
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdees.farmdataexercise.R
import com.erdees.farmdataexercise.coreUtils.components.MyTopAppBar
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.components.ProgressBar
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.components.Toast
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.selectFarm.utils.WindowAdapter
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.selectFarm.utils.rememberMapViewWithLifecycle
import com.erdees.farmdataexercise.model.Response
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.model.*
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
                is Response.Success ->  AndroidView({ mapView }) { mapView ->
                    CoroutineScope(Dispatchers.Main).launch {
                       val adapter = WindowAdapter(LayoutInflater.from(context),context)
                        val map = mapView.awaitMap()
                        map.setInfoWindowAdapter(
                            adapter
                        )
                     /**   map.setOnMarkerClickListener {
                            Log.i("Select farm screen", "Click!")
                            Log.i("Select farm screen", "Click! ${it.title}")
                            true
                        }*/
                        map.setOnInfoWindowCloseListener { adapter.drawable = null
                        }
                        map.uiSettings.isZoomControlsEnabled = true

                        farmsInformationResponse.data.forEach {
                            Log.i("Select Farm Screen" , it.toString())
                            map.markFarmOnTheMap(it.locationName,LatLng(it.geoPoint.latitude, it.geoPoint.longitude),it.farmImageUrl,
                                context)
                        }
                        map.centerMapAtHelsinki()


                    }
                }
                is Response.Error -> Toast(farmsInformationResponse.message)
            }

        }
    }
}

fun GoogleMap.markFarmOnTheMap(locationName : String, latLng: LatLng,farmImageUrl : String,context: Context) : Marker {
    Log.i("Select farm screen", farmImageUrl)
    val markerOption = MarkerOptions()
        .title(locationName)
        .position(latLng)
        .snippet(farmImageUrl)
        .icon(bitmapDescriptorFromVector( context,R.drawable.ic_farm_map_marker))
    return this.addMarker(markerOption)
}

fun GoogleMap.centerMapAtHelsinki(){
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

