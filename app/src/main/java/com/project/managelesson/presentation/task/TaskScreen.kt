
package com.project.managelesson.presentation.task

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.project.managelesson.presentation.common_components.DeleteDialog
import com.project.managelesson.presentation.common_components.TaskCheckBox
import com.project.managelesson.presentation.task.components.SubjectsBottomSheet
import com.project.managelesson.presentation.task.components.TaskDatePickerDialog
import com.project.managelesson.presentation.theme.Blue
import com.project.managelesson.test
import com.project.managelesson.utils.Priority
import com.project.managelesson.utils.millisToDateString
import kotlinx.coroutines.launch
import java.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen() {

    val titleState = remember {
        mutableStateOf("")
    }
    val descriptionState = remember {
        mutableStateOf("")
    }

    var deleteTaskDialogState by rememberSaveable {
        mutableStateOf(false)
    }

    val taskDatePicker = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )
    var openTaskDatePickerState by rememberSaveable {
        mutableStateOf(false)
    }

    val sheetState = rememberModalBottomSheetState()
    var openBottomSheet by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    

    DeleteDialog(
        onClickConfirmButton = { deleteTaskDialogState = false },
        onDismissRequest = { deleteTaskDialogState = false },
        title = "Delete task",
        text = "Do you want to delete task?",
        isOpen = deleteTaskDialogState
    )

    TaskDatePickerDialog(
        state = taskDatePicker,
        isOpen = openTaskDatePickerState,
        onClickDismissRequest = { openTaskDatePickerState = false },
        onClickConfirmButton = { openTaskDatePickerState = false }
    )

    SubjectsBottomSheet(
        sheetState = sheetState,
        isOpen = openBottomSheet,
        subjectList = test,
        onClickDismiss = { openBottomSheet = false },
        onClickSubject = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible)
                    openBottomSheet = false
            }
        }
    )

    Scaffold(
        topBar = {
            TaskTopBar(
                isCompleted = false,
                isTaskExist = true,
                checkBoxColor = Blue,
                onBackClick = {  },
                onCheckBoxClick = {  },
                onDeleteClick = { deleteTaskDialogState = true }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 12.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = titleState.value,
                onValueChange = { titleState.value = it },
                label = { Text(text = "Title") },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = descriptionState.value,
                onValueChange = { descriptionState.value = it },
                label = { Text(text = "Description") },
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Date",
                style = MaterialTheme.typography.bodySmall
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = taskDatePicker.selectedDateMillis.millisToDateString(),
                    style = MaterialTheme.typography.bodyLarge
                )
                IconButton(onClick = { openTaskDatePickerState = true }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Priority",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Priority.entries.forEach {
                    Spacer(modifier = Modifier.width(12.dp))
                    PriorityIconButton(
                        modifier = Modifier.weight(1f),
                        title = it.title,
                        onClick = {  },
                        backgroundColor = it.color,
                        borderColor = if (it == Priority.MEDIUM) Color.White else Color.Transparent,
                        titleColor = if (it == Priority.MEDIUM) Color.White else Color.Black.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Related to subject",
                style = MaterialTheme.typography.bodySmall
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "English",
                    style = MaterialTheme.typography.bodyLarge
                )
                IconButton(onClick = { openBottomSheet = true }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Select subject"
                    )
                }
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                onClick = { /*TODO*/ }
            ) {
                Text(text = "Save task")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskTopBar(
    isCompleted: Boolean,
    isTaskExist: Boolean,
    checkBoxColor: Color,
    onBackClick: () -> Unit,
    onCheckBoxClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        title = {
            Text(
                text = "Task",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        actions = {
            if (isTaskExist) {
                TaskCheckBox(
                    isComplete = isCompleted,
                    onClick = onCheckBoxClick,
                    color = checkBoxColor
                )
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete task"
                    )
                }
            }
        }
    )
}

@Composable
private fun PriorityIconButton(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit,
    backgroundColor: Color,
    borderColor: Color,
    titleColor: Color
) {
    Box(
        modifier = modifier
            .background(backgroundColor)
            .padding(5.dp)
            .border(1.dp, borderColor, RoundedCornerShape(8.dp))
            .padding(5.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = title, color = titleColor)
    }
}