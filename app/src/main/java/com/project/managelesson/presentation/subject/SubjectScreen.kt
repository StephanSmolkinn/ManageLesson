package com.project.managelesson.presentation.subject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.Navigation.findNavController
import com.project.managelesson.domain.model.Lesson
import com.project.managelesson.domain.model.Subject
import com.project.managelesson.domain.model.Task
import com.project.managelesson.presentation.common_components.AddDialog
import com.project.managelesson.presentation.common_components.DeleteDialog
import com.project.managelesson.presentation.dashboard.components.CountCard
import com.project.managelesson.presentation.common_components.lessonList
import com.project.managelesson.presentation.common_components.taskList
import com.project.managelesson.utils.Screen
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectScreen(
    navController: NavController,
    subjectId: Int?,
    viewModel: SubjectViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    val stateList = rememberLazyListState()
    val stateFabExpanded = remember {
        derivedStateOf { stateList.firstVisibleItemIndex == 0 }
    }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val scaffoldState = remember {
        SnackbarHostState()
    }

    var addDialogState by rememberSaveable {
        mutableStateOf(false)
    }
    var deleteLessonDialogState by rememberSaveable {
        mutableStateOf(false)
    }
    var deleteSubjectDialogState by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is SubjectViewModel.UiEvent.ShowSnackBar -> {
                    scaffoldState.showSnackbar(message = event.message, duration = SnackbarDuration.Long)
                }

                SubjectViewModel.UiEvent.UpdateSubject -> {
                    addDialogState = false
                }

                SubjectViewModel.UiEvent.DeleteSubject -> {
                    navController.navigateUp()
                }
            }
        }
    }
    
    LaunchedEffect(key1 = state.studyHours, key2 = state.goalLessonHours) {
        viewModel.onEvent(SubjectEvent.UpdateProgress)
    }

    AddDialog(
        onClickConfirmButton = {
            viewModel.onEvent(SubjectEvent.UpdateSubject)
            addDialogState = false
        },
        onDismissRequest = { addDialogState = false },
        title = "Update Subject",
        isOpen = addDialogState,
        subjectName = state.subjectName,
        goalHours = state.goalLessonHours,
        onChangeName = { viewModel.onEvent(SubjectEvent.OnSubjectNameChange(it)) },
        onChangeGoalHours = { viewModel.onEvent(SubjectEvent.OnSubjectGoalHourChange(it)) },
        selectedColor = state.cardColors,
        onChangeColor = { viewModel.onEvent(SubjectEvent.OnSubjectCardColorChange(it)) }
    )

    DeleteDialog(
        onClickConfirmButton = {
            deleteLessonDialogState = false
            viewModel.onEvent(SubjectEvent.DeleteLesson)
        },
        onDismissRequest = { deleteLessonDialogState = false },
        title = "Delete Lesson",
        text = "Do you want to delete lesson",
        isOpen = deleteLessonDialogState
    )

    DeleteDialog(
        onClickConfirmButton = {
            deleteSubjectDialogState = false
            viewModel.onEvent(SubjectEvent.DeleteSubject)
        },
        onDismissRequest = { deleteSubjectDialogState = false },
        title = "Delete Subject",
        text = "Do you want to delete subject",
        isOpen = deleteSubjectDialogState
    )


    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SubjectTopBar(
                scrollBehavior = scrollBehavior,
                title = state.subjectName,
                onBackClick = { navController.navigateUp() },
                onDeleteClick = { deleteSubjectDialogState = true },
                onEditClick = { addDialogState = true }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    val taskId = null
                    navController.navigate("${Screen.TaskScreen.route}?taskId={$taskId}&taskSubjectId=${subjectId}")
                },
                icon = {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                },
                text = { Text(text = "Add task") },
                shape = CircleShape,
                expanded = stateFabExpanded.value
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = scaffoldState)
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            state = stateList
        ) {
            item {
                ViewSection(
                    goalHours = state.goalLessonHours,
                    studiedHours = "%.2f".format(state.studyHours),
                    progressStudy = state.progress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    color = state.cardColors[0]
                )
            }
            taskList(
                title = "Upcoming tasks",
                taskList = state.upcomingTaskList,
                text = "You dont have any task",
                onClickCard = { taskId ->
                    navController.navigate("${Screen.TaskScreen.route}?taskId=${taskId}&taskSubjectId=${subjectId}")
                },
                onClickCheckBox = { viewModel.onEvent(SubjectEvent.OnTaskCompleteChange(it)) }
            )
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
            taskList(
                title = "Completed tasks",
                taskList = state.completedTaskList,
                text = "You dont have any completed task",
                onClickCard = { taskId ->
                    navController.navigate("${Screen.TaskScreen.route}?taskId=${taskId}&taskSubjectId=${subjectId}")
                },
                onClickCheckBox = { viewModel.onEvent(SubjectEvent.OnTaskCompleteChange(it)) }
            )
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
            lessonList(
                title = "Recent lessons",
                lessonList = state.recentLessonList,
                text = "You dont have any lesson",
                onClickDelete = {
                    deleteLessonDialogState = true
                    viewModel.onEvent(SubjectEvent.OnDeleteLesson(it))
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubjectTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    title: String,
    onBackClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
) {
    LargeTopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back"
                )
            }
        },
        title = {
            Text(
                text = title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        actions = {
            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete subject"
                )
            }
            IconButton(onClick = onEditClick) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit subject"
                )
            }
        }
    )
}

@Composable
private fun ViewSection(
    modifier: Modifier = Modifier,
    goalHours: String,
    studiedHours: String,
    progressStudy: Double,
    color: Color
) {

    val progressState = remember(progressStudy) {
        (progressStudy * 100).toInt().coerceIn(0, 100)
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CountCard(
            modifier = Modifier.weight(1f),
            title = "Goal hours",
            count = goalHours
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            title = "Studied hours",
            count = studiedHours
        )
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier.size(75.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                progress = 1f,
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                progress = progressStudy.toFloat(),
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round,
                color = color
            )
            Text(text = "$progressState%")
        }
    }
}