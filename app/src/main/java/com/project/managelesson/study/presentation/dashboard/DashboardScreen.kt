package com.project.managelesson.study.presentation.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.project.managelesson.R
import com.project.managelesson.core.navigation.Screen
import com.project.managelesson.core.presentation.common_components.AddDialog
import com.project.managelesson.core.presentation.common_components.DeleteDialog
import com.project.managelesson.core.presentation.common_components.lessonList
import com.project.managelesson.core.presentation.common_components.taskList
import com.project.managelesson.study.domain.model.Subject
import com.project.managelesson.study.presentation.dashboard.components.CountCard
import com.project.managelesson.study.presentation.dashboard.components.EmptyListSection
import com.project.managelesson.study.presentation.dashboard.components.SubjectCard
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: DashboardViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()
    val taskList by viewModel.taskList.collectAsState()
    val lessonList by viewModel.lessonEntityList.collectAsState()

    var addDialogState by rememberSaveable {
        mutableStateOf(false)
    }
    var deleteDialogState by rememberSaveable {
        mutableStateOf(false)
    }

    AddDialog(
        onClickConfirmButton = {
            viewModel.onEvent(DashboardEvent.SaveSubject)
            addDialogState = false
        },
        onDismissRequest = { addDialogState = false },
        title = "Add Subject",
        isOpen = addDialogState,
        subjectName = state.subjectName,
        goalHours = state.goalLessonHours,
        onChangeName = { viewModel.onEvent(DashboardEvent.OnSubjectNameChange(it)) },
        onChangeGoalHours = { viewModel.onEvent(DashboardEvent.OnSubjectHoursChange(it)) },
        selectedColor = state.subjectCardColor,
        onChangeColor = { viewModel.onEvent(DashboardEvent.OnSubjectCardColorChange(it)) }
    )

    DeleteDialog(
        onClickConfirmButton = {
            viewModel.onEvent(DashboardEvent.DeleteLesson)
            deleteDialogState = false
        },
        onDismissRequest = { deleteDialogState = false },
        title = "Delete Lesson",
        text = "Do you want to delete lesson",
        isOpen = deleteDialogState
    )

    val scaffoldState = remember {
        SnackbarHostState()
    }
    
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is DashboardViewModel.UiEvent.ShowSnackBar -> {
                    scaffoldState.showSnackbar(message = event.message)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            DashboardTopBar(
                onButtonAccountClick = { navController.navigate(Screen.SignInScreen.route) }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = scaffoldState)
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            item {
                CountCardSection(
                    subjectCount = state.totalSubjectCount,
                    studiedHours = "%.2f".format(state.totalLessonHours),
                    goalHours = "${state.totalLessonGoalHours}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                )
            }
            item {
                SubjectCardSection(
                    subjectList = state.subjects,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        addDialogState = true
                    },
                    onSubjectClick = { subjectId ->
                        navController.navigate("${Screen.SubjectScreen.route}?subjectId=${subjectId}")
                    }
                )
            }
            item {
                Button(
                    onClick = { navController.navigate(Screen.LessonScreen.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp, vertical = 18.dp)
                ) {
                    Text(
                        text = "Start lesson",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
            taskList(
                title = "Upcoming tasks",
                taskList = taskList,
                text = "You dont have any task",
                onClickCard = { taskId ->
                    val subjectId = null
                    navController.navigate("${Screen.TaskScreen.route}?taskId=$taskId&taskSubjectId=${subjectId}")
                },
                onClickCheckBox = { task ->
                    viewModel.onEvent(DashboardEvent.OnTaskChange(task))
                }
            )
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
            lessonList(
                title = "Recent lessons",
                lessonEntityList = lessonList,
                text = "You dont have any lesson",
                onClickDelete = { lesson ->
                    viewModel.onEvent(DashboardEvent.OnDeleteLesson(lesson))
                    deleteDialogState = true
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardTopBar(
    onButtonAccountClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Manage Lesson",
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(onClick = onButtonAccountClick) {
                Icon(
                    imageVector = Icons.Rounded.AccountCircle,
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
private fun CountCardSection(
    modifier: Modifier = Modifier,
    subjectCount: Int,
    studiedHours: String,
    goalHours: String
) {
    Row(
        modifier = modifier
    ) {
        CountCard(
            modifier = Modifier.weight(1f),
            title = "Subject count",
            count = "$subjectCount"
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            title = "Studied hours",
            count = studiedHours
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            title = "Goal hours",
            count = goalHours
        )
    }
}

@Composable
private fun SubjectCardSection(
    modifier: Modifier = Modifier,
    subjectList: List<Subject>,
    onClick: () -> Unit,
    onSubjectClick: (Int?) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Subjects",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 12.dp)
            )
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add subject"
                )
            }
        }
        if (subjectList.isEmpty()) {
            EmptyListSection(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally),
                text = "You dont have any subject",
                image = R.drawable.books
            )
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp)
        ) {
            items(subjectList) {
                SubjectCard(
                    title = it.title,
                    color = it.color.map { Color(it) },
                    onClick = { onSubjectClick(it.id) }
                )
            }
        }
    }
}