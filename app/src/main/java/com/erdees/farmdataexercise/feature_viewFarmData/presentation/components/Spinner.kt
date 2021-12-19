package com.erdees.farmdataexercise.feature_viewFarmData.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
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

@Composable
fun Spinner(
    onValueChange: (String) -> Unit,
    textToPresent: String,
    list: List<String>,
    modifier: Modifier = Modifier,
    firstColor: Color,
    dropDownMenuColor: Color
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(-1) }
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10))
            .height(50.dp)
            .width(300.dp)
            .background(
                firstColor
            )
            .clickable(onClick = { expanded = true }), contentAlignment = Alignment.CenterStart
    ) {
        Text(
            if (selectedIndex == -1) textToPresent else list[selectedIndex],
            modifier = Modifier
                .padding(12.dp, 0.dp, 12.dp, 0.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.W700
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(dropDownMenuColor)
                .width(300.dp)

        ) {
            list.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    onValueChange(list[selectedIndex])
                    expanded = false
                }) {
                    Text(text = list[index],fontSize = 16.sp,fontWeight = FontWeight.W500)
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewSpinner() {
    Spinner(
        onValueChange = {},
        textToPresent = stringResource(id = R.string.sensorType),
        list = Constants.FARM_LIST,
        modifier = Modifier.padding(horizontal = 40.dp),
        Color.Gray,
        Color.LightGray
    )
}


