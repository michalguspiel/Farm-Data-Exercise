package com.erdees.farmdataexercise.feature_viewFarmData.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erdees.farmdataexercise.R
import com.erdees.farmdataexercise.coreUtils.Constants
import com.erdees.farmdataexercise.ui.theme.OnPrimaryLight
import com.erdees.farmdataexercise.ui.theme.Yellow100

@Composable
fun Spinner(
    modifier: Modifier = Modifier,
    onValueChange: ((String, String) -> Unit) = { _, _ -> },
    textToPresent: String,
    firebaseDocumentsList: List<String>,
    spinnerItemsList: List<String>,
    firstColor: Color,
    dropDownMenuColor: Color
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(-1) }
    val width = 240.dp
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14))
            .height(50.dp)
            .width(width)
            .background(
                firstColor
            )
            .border(2.dp, OnPrimaryLight)
            .clickable(onClick = { expanded = true }), contentAlignment = Alignment.CenterStart
    ) {
        Row() {
            Text(
                if (selectedIndex == -1) textToPresent else spinnerItemsList[selectedIndex],
                modifier = Modifier
                    .padding(12.dp, 0.dp, 12.dp, 0.dp).weight(0.8f),
                fontSize = 18.sp,
                fontWeight = FontWeight.W700
            )
            Icon(
                imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = "Expand icon",
                modifier = Modifier.weight(0.2f)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(dropDownMenuColor)
                .width(width)
                .border(1.dp, Color.Gray)
        ) {
            firebaseDocumentsList.forEachIndexed { index, s ->
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


