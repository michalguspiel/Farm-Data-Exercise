package com.erdees.farmdataexercise.feature_viewFarmData.presentation.selectFarm.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import coil.ImageLoader
import coil.request.ImageRequest
import com.erdees.farmdataexercise.R
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.model.Marker


/**This is adapter which sets how the farm information window should be displayed on the map.
 * Although whole project is made with Jetpack compose, this works on views since GoogleMaps SDK is not compatible with Jetpack compose.*/
class WindowAdapter(val context: Context) :

    GoogleMap.InfoWindowAdapter {
    var drawable: Drawable? = null


    override fun getInfoWindow(p0: Marker): View? {
        return null
    }

    @SuppressLint("InflateParams")
    override fun getInfoContents(marker: Marker): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.popup_window, null)

        val title = view.findViewById<TextView>(R.id.title)
        val image = view.findViewById<ImageView>(R.id.farm_pic)

        title.text = marker.title

        if (drawable != null) {
            image.setImageDrawable(drawable)
        }
        val imageLoader = ImageLoader.Builder(context).build()

        val request = ImageRequest.Builder(context).data(marker.snippet).allowHardware(false).target(
            onSuccess = { result ->
                Log.i("TAG", "success $result")
                drawable = result
                marker.showInfoWindow()
            },
            onError = { error ->
                Log.i("TAG", "error no picture" + error.toString())
                drawable = AppCompatResources.getDrawable(context,R.drawable.ic_baseline_house_24)
                image.setImageDrawable(drawable)
            }
        )
            .build()
        if (drawable == null) imageLoader.enqueue(request)
        return view
    }
}
