package com.erdees.farmdataexercise.coreUtils.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.erdees.farmdataexercise.ui.theme.Yellow100

@Composable
fun MyButton(onClick: () -> Unit,text : String,enabled: Boolean = true,) {
    androidx.compose.material.Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = Yellow100),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 10.dp,
            pressedElevation = 16.dp,
            disabledElevation = 0.dp
        ),
        shape = RoundedCornerShape(4.dp), enabled = enabled
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp),
            fontWeight = FontWeight.W500
        )
    }

}