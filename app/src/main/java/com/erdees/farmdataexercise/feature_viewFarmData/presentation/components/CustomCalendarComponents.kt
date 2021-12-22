package com.erdees.farmdataexercise.feature_viewFarmData.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout
import io.github.boguszpawlowski.composecalendar.header.MonthState

@Composable
fun CalendarHeader (
    monthState: MonthState,
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(
        modifier = modifier.fillMaxWidth().padding(horizontal = 12.dp),
    ) {
        val (leftArrow, rightArrow, text) = createRefs()
        IconButton(
            modifier = Modifier.testTag("Decrement").constrainAs(leftArrow){start.linkTo(parent.start)},
            onClick = { monthState.currentMonth = monthState.currentMonth.minusMonths(1)
                }
        ) {
            Image(
                imageVector = Icons.Default.KeyboardArrowLeft,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
                contentDescription = "Previous",
            )
        }
        Row(modifier = Modifier.constrainAs(text){start.linkTo(leftArrow.end)
            end.linkTo(rightArrow.start)}){
        Text(
            modifier = Modifier.testTag("MonthLabel"),
            text = monthState.currentMonth.month.name.lowercase().replaceFirstChar { it.titlecase() },
            style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = monthState.currentMonth.year.toString(), style = MaterialTheme.typography.h5)
        }
        IconButton(
            modifier = Modifier.testTag("Increment").constrainAs(rightArrow){end.linkTo(parent.end)},
            onClick = { monthState.currentMonth = monthState.currentMonth.plusMonths(1) }
        ) {
            Image(
                imageVector = Icons.Default.KeyboardArrowRight,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
                contentDescription = "Next",
            )
        }
    }
}