package com.erdees.farmdataexercise.coreUtils.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.erdees.farmdataexercise.R
import com.erdees.farmdataexercise.ui.theme.LocalCorner
import com.erdees.farmdataexercise.ui.theme.LocalElevation
import com.erdees.farmdataexercise.ui.theme.LocalSpacing
import com.erdees.farmdataexercise.ui.theme.Yellow100

@Composable
fun MyButton(onClick: () -> Unit,text : String,enabled: Boolean = true, icon: ImageVector? = null) {
    androidx.compose.material.Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = Yellow100),
        elevation = ButtonDefaults.elevation(
            defaultElevation = LocalElevation.current.medium,
            pressedElevation = LocalElevation.current.large,
            disabledElevation = 0.dp
        ),
        shape = RoundedCornerShape(LocalCorner.current.small), enabled = enabled
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = LocalSpacing.current.medium, vertical = LocalSpacing.current.xSmall),
            fontWeight = FontWeight.W500
        )
        if(icon != null) {
           Icon(imageVector = icon, contentDescription =  stringResource(id = R.string.icon))
        }

    }
}

