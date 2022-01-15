package com.erdees.farmdataexercise.feature_FarmData.presentation.farmDataScreen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.erdees.farmdataexercise.ui.theme.Green400
import com.erdees.farmdataexercise.ui.theme.LocalSpacing
import com.erdees.farmdataexercise.ui.theme.OnPrimaryLightest
import com.erdees.farmdataexercise.ui.theme.Typography

@Composable
fun RowScope.TableCell(
    modifier : Modifier = Modifier,
    text: String,
    weight: Float,
    style: TextStyle = Typography.body2,
    textColor: Color = OnPrimaryLightest
) {
    Text(
        text = text, style = style,
        modifier = modifier
            .border(1.dp, Green400)
            .weight(weight)
            .padding(
                horizontal = LocalSpacing.current.xSmall,
                vertical = LocalSpacing.current.xxSmall
            ),
        color = textColor,
    )
}