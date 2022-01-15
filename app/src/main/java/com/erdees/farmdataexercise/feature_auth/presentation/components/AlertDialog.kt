package com.erdees.farmdataexercise.feature_auth.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AlertDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    title: @Composable (() -> Unit)? = null,
    dismissButton: @Composable (() -> Unit)? = null,
    text: @Composable (() -> Unit)? = null,
) {

    androidx.compose.material.AlertDialog(
        modifier = modifier, onDismissRequest = onDismissRequest,
        title = title,
        text = text,
        confirmButton = {},
        dismissButton = dismissButton
    )

}