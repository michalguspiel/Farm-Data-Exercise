package com.erdees.farmdataexercise.coreUtils.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.erdees.farmdataexercise.coreUtils.TestTags

@Composable
fun ProgressBar() {
    Box(
        modifier = Modifier.fillMaxSize().testTag(TestTags.PROGRESS_BAR_TAG),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator()
    }
}