package com.project.managelesson.core.presentation.common_components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteDialog(
    onClickConfirmButton: () -> Unit,
    onDismissRequest: () -> Unit,
    title: String,
    text: String,
    isOpen: Boolean,
) {
    if (isOpen) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            text = {
                Text(text = text)
            },
            title = { Text(text = title) },
            confirmButton = {
                TextButton(onClick = onClickConfirmButton) {
                    Text(text = "Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(text = "Back")
                }
            }
        )
    }
}