package com.project.managelesson.presentation.task.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDatePickerDialog(
    state: DatePickerState,
    isOpen: Boolean,
    onClickDismissRequest: () -> Unit,
    onClickConfirmButton: () -> Unit,
) {
    if (isOpen) {
        DatePickerDialog(
            onDismissRequest = onClickDismissRequest,
            confirmButton = {
                TextButton(onClick = onClickConfirmButton) {
                    Text(text = "Ok")
                }
            },
            dismissButton = {
                TextButton(onClick = onClickDismissRequest) {
                    Text(text = "Cancel")
                }
            }
        ) {
            DatePicker(
                state = state,
                dateValidator = {
                    val selectedDate = Instant
                        .ofEpochMilli(it)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()

                    val currentDate = LocalDate.now(ZoneId.systemDefault())
                    selectedDate >=currentDate
                }
            )
        }
    }
}