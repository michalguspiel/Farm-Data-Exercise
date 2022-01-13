package com.erdees.farmdataexercise.feature_FarmData.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.erdees.farmdataexercise.R
import com.erdees.farmdataexercise.ui.theme.BackgroundColor
import com.erdees.farmdataexercise.ui.theme.LocalCorner
import com.erdees.farmdataexercise.ui.theme.LocalElevation
import com.erdees.farmdataexercise.ui.theme.Typography

@Composable
fun FarmCard(locationName: String, farmImageUrl: String) {
    Card(
        shape = RoundedCornerShape(LocalCorner.current.default),
        elevation = LocalElevation.current.large,
        modifier = Modifier.padding(LocalElevation.current.default)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(BackgroundColor)
        ) {
            Text(
                text = locationName,
                style = Typography.h4, textAlign = TextAlign.Center
            )
            Image(
                rememberImagePainter(
                    data = farmImageUrl
                ),
                contentDescription = stringResource(id = R.string.farm_pic),
                Modifier
                    .size(height = 128.dp, width = 256.dp)
                    .padding(vertical = (LocalElevation.current.default))
            )
        }
    }
}