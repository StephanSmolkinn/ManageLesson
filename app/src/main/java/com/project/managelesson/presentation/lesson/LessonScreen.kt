package com.project.managelesson.presentation.lesson

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.project.managelesson.lessons
import com.project.managelesson.presentation.common_components.DeleteDialog
import com.project.managelesson.presentation.common_components.lessonList
import com.project.managelesson.presentation.task.components.SubjectsBottomSheet
import com.project.managelesson.test
import com.project.managelesson.utils.Constants.SERVICE_ACTION_CANCEL
import com.project.managelesson.utils.Constants.SERVICE_ACTION_START
import com.project.managelesson.utils.Constants.SERVICE_ACTION_STOP
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonScreen(
    navController: NavController,
    viewModel: LessonViewModel = hiltViewModel(),
    timerService: LessonTimerService
) {
    val context = LocalContext.current

    var deleteLessonDialogState by rememberSaveable {
        mutableStateOf(false)
    }

    val sheetState = rememberModalBottomSheetState()
    var openBottomSheet by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()

    DeleteDialog(
        onClickConfirmButton = { deleteLessonDialogState = false },
        onDismissRequest = { deleteLessonDialogState = false },
        title = "Delete lesson",
        text = "Do you want to delete lesson?",
        isOpen = deleteLessonDialogState
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
        topBar = { LessonTopBar(onBackClick = { navController.navigateUp() }) }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            item {
                TimerLesson(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    hours = timerService.hours.value,
                    minutes = timerService.minutes.value,
                    seconds = timerService.seconds.value
                )
            }
            item {
                RelateSubject(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    subject = "English",
                    onClickSubject = { openBottomSheet = true }
                )
            }
            item {
                ButtonsControl(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    onStartClick = {
                        ServiceHelper.triggerService(
                            context = context,
                            action = if (timerService.timerState.value == TimerState.Start)
                                SERVICE_ACTION_STOP
                            else
                                SERVICE_ACTION_START
                        )
                    },
                    onCancelClick = {
                        ServiceHelper.triggerService(
                            context = context,
                            action = SERVICE_ACTION_CANCEL
                        )
                    },
                    onFinishClick = {
                        ServiceHelper.triggerService(
                            context = context,
                            action = SERVICE_ACTION_STOP
                        )
                    },
                    timerState = timerService.timerState.value
                )
            }
            item {
                Spacer(modifier = Modifier.height(10.dp))
            }
            lessonList(
                title = "Lessons history",
                lessonList = lessons,
                text = "You dont have any lesson",
                onClickDelete = { deleteLessonDialogState = true }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LessonTopBar(
    onBackClick: () -> Unit
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
                text = "Lesson",
                style = MaterialTheme.typography.headlineSmall
            )
        }
    )
}

@Composable
private fun TimerLesson(
    modifier: Modifier = Modifier,
    hours: String,
    minutes: String,
    seconds: String
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(250.dp)
                .border(5.dp, MaterialTheme.colorScheme.onSurfaceVariant, CircleShape)
        )
        Row {
            AnimatedContent(targetState = hours, label = hours) {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 40.sp)
                )
            }
            AnimatedContent(targetState = minutes, label = minutes) {
                Text(
                    text = ":$it:",
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 40.sp)
                )
            }
            AnimatedContent(targetState = seconds, label = seconds) {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 40.sp)
                )
            }
        }
    }
}

@Composable
private fun RelateSubject(
    modifier: Modifier = Modifier,
    subject: String,
    onClickSubject: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
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
                text = subject,
                style = MaterialTheme.typography.bodyLarge
            )
            IconButton(onClick = onClickSubject) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Select subject"
                )
            }
        }
    }
}

@Composable
private fun ButtonsControl(
    modifier: Modifier = Modifier,
    onStartClick: () -> Unit,
    onCancelClick: () -> Unit,
    onFinishClick: () -> Unit,
    timerState: TimerState
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = onCancelClick,
            enabled = timerState != TimerState.Start
        ) {
            Text(
                text = "Cancel",
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            )
        }
        Button(onClick = onStartClick) {
            Text(
                text = when (timerState) {
                    TimerState.Closed -> "Start"
                    TimerState.Start -> "Stop"
                    TimerState.Stop -> "Resume"
                },
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            )
        }
        Button(
            onClick = onFinishClick,
            enabled = timerState != TimerState.Start
        ) {
            Text(
                text = "Finish",
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            )
        }
    }
}