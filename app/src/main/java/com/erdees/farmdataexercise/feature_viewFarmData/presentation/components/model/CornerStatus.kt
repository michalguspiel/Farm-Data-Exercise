package com.erdees.farmdataexercise.feature_viewFarmData.presentation.components.model

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

sealed class HorizontalCorner {
    object StartCorner : HorizontalCorner()
    object EndCorner : HorizontalCorner()
}

sealed class VerticalCorner {
    object TopCorner : VerticalCorner()
    object BottomCorner : VerticalCorner()
}

/**
 * Class which helps to detect which corner should stay sharp in RoundedCornerShape, meaning without rounding.
 * Button, or element with one sharp corner points in some direction.
 * */
open class CornerStatus(
    val horizontalCorner: HorizontalCorner,
    val verticalCorner: VerticalCorner
) {

    object TopEndCorner : CornerStatus(HorizontalCorner.EndCorner, VerticalCorner.TopCorner)
    object BottomEndCorner : CornerStatus(HorizontalCorner.EndCorner, VerticalCorner.BottomCorner)
    object BottomStartCorner :
        CornerStatus(HorizontalCorner.StartCorner, VerticalCorner.BottomCorner)

    object TopStartCorner : CornerStatus(HorizontalCorner.StartCorner, VerticalCorner.TopCorner)


    private fun cornerStatus(): CornerStatus {
        return when (horizontalCorner) {
            HorizontalCorner.StartCorner -> when (verticalCorner) {
                VerticalCorner.TopCorner -> TopStartCorner
                VerticalCorner.BottomCorner -> BottomStartCorner
            }
            else -> when (verticalCorner) {
                VerticalCorner.TopCorner -> TopEndCorner
                VerticalCorner.BottomCorner -> BottomEndCorner
            }
        }
    }

    @Composable
    fun provideShape(rounding: Dp): RoundedCornerShape {
        return when (cornerStatus()) {
            TopEndCorner -> RoundedCornerShape(
                topEnd = 0.dp,
                bottomEnd = rounding,
                bottomStart = rounding,
                topStart = rounding
            )
            BottomEndCorner -> RoundedCornerShape(
                topEnd = rounding,
                bottomEnd = 0.dp,
                bottomStart = rounding,
                topStart = rounding
            )
            BottomStartCorner -> RoundedCornerShape(
                topEnd = rounding,
                bottomEnd = rounding,
                bottomStart = 0.dp,
                topStart = rounding
            )
            TopStartCorner -> RoundedCornerShape(
                topEnd = rounding,
                bottomEnd = rounding,
                bottomStart = rounding,
                topStart = 0.dp
            )
            else -> RoundedCornerShape(rounding)
        }
    }


}
