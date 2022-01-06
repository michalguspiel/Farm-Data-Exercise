package com.erdees.farmdataexercise.feature_viewFarmData.presentation.components.model
import com.erdees.farmdataexercise.feature_viewFarmData.presentation.farmData.DetailedFarmDataGraphScreen

sealed class HorizontalCorner {
    object StartCorner : HorizontalCorner()
    object EndCorner : HorizontalCorner()
}

sealed class VerticalCorner {
    object TopCorner : VerticalCorner()
    object BottomCorner : VerticalCorner()
}
/**
 * It's a class which helps to detect which corner should stay sharp in [DetailedFarmDataGraphScreen], meaning without rounding.
 * */
open class CornerStatus(val horizontalCorner : HorizontalCorner, val verticalCorner : VerticalCorner) {

object TopEndCorner : CornerStatus(HorizontalCorner.EndCorner, VerticalCorner.TopCorner)
object BottomEndCorner : CornerStatus(HorizontalCorner.EndCorner, VerticalCorner.BottomCorner)
object BottomStartCorner : CornerStatus(HorizontalCorner.StartCorner, VerticalCorner.BottomCorner)
object TopStartCorner : CornerStatus(HorizontalCorner.StartCorner, VerticalCorner.TopCorner)


    fun cornerStatus(): CornerStatus {
        return when(horizontalCorner) {
           HorizontalCorner.StartCorner -> when(verticalCorner){
               VerticalCorner.TopCorner -> TopStartCorner
               VerticalCorner.BottomCorner -> BottomStartCorner
           }
           else -> when(verticalCorner){
               VerticalCorner.TopCorner -> TopEndCorner
               VerticalCorner.BottomCorner -> BottomEndCorner
           }
       }
    }

}
