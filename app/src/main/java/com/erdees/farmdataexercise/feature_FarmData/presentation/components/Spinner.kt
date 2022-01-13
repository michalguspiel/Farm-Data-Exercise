package com.erdees.farmdataexercise.feature_FarmData.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erdees.farmdataexercise.R
import com.erdees.farmdataexercise.coreUtils.Constants
import com.erdees.farmdataexercise.coreUtils.utils.Util.toDp
import com.erdees.farmdataexercise.ui.theme.LocalCorner
import com.erdees.farmdataexercise.ui.theme.LocalSpacing
import com.erdees.farmdataexercise.ui.theme.OnPrimaryLight
import com.erdees.farmdataexercise.ui.theme.Yellow100

@Composable
fun Spinner(
    modifier: Modifier = Modifier,
    onValueChange: ((String, String) -> Unit) = { _, _ -> },
    textToPresent: String,
    width : Dp = 240.dp,
    firebaseDocumentsList: List<String>,
    spinnerItemsList: List<String>,
    firstColor: Color,
    dropDownMenuColor: Color,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(-1) }

    var boxSize by remember {mutableStateOf(0f)}
    val density = LocalDensity.current

    Box(
        modifier = modifier
            .background(
                firstColor,RoundedCornerShape(LocalCorner.current.small)
            ).onGloballyPositioned { boxSize =  it.size.width.toFloat().toDp(density) }
            .border(1.dp, OnPrimaryLight,RoundedCornerShape(LocalCorner.current.small))
            .height(50.dp)
            .width(width)
            .clickable(onClick = { expanded = true }), contentAlignment = Alignment.CenterStart


    ) {
        Row {
            Text(
                if (selectedIndex == -1) textToPresent else spinnerItemsList[selectedIndex],
                modifier = Modifier
                    .padding(LocalSpacing.current.default, 0.dp, LocalSpacing.current.default, 0.dp)
                    .weight(0.8f),
                fontSize = 18.sp,
                fontWeight = FontWeight.W700
            )
            Icon(
                imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = stringResource(id = R.string.expand_icon),
                modifier = Modifier.weight(0.2f)
            )
        }
        MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(LocalCorner.current.small))) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(
                        dropDownMenuColor
                    )

                    .border(
                        1.dp,
                        Color.Gray,RoundedCornerShape(LocalCorner.current.small)
                    ).width(boxSize.dp)
            ) {
                firebaseDocumentsList.forEachIndexed { index, _ ->
                    DropdownMenuItem(onClick = {
                        selectedIndex = index
                        onValueChange(
                            firebaseDocumentsList[selectedIndex],
                            spinnerItemsList[selectedIndex]
                        )
                        expanded = false
                    }) {
                        Text(
                            text = spinnerItemsList[index],
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W500,
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewSpinner() {
    Spinner(
        textToPresent = stringResource(id = R.string.sensorType),
        firebaseDocumentsList = Constants.SENSOR_LIST.map { it.firebaseName },
        spinnerItemsList = Constants.SENSOR_LIST.map { it.presentationName },
        modifier = Modifier.padding(horizontal = 40.dp),
        firstColor = Yellow100,
        dropDownMenuColor = Color.LightGray
    )
}


