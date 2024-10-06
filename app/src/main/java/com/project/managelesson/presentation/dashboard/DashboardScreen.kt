package com.project.managelesson.presentation.dashboard

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
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.managelesson.R
import com.project.managelesson.domain.model.Lesson
import com.project.managelesson.domain.model.Subject
import com.project.managelesson.domain.model.Task
import com.project.managelesson.lessons
import com.project.managelesson.presentation.common_components.AddDialog
import com.project.managelesson.presentation.common_components.DeleteDialog
import com.project.managelesson.presentation.dashboard.components.CountCard
import com.project.managelesson.presentation.dashboard.components.EmptyListSection
import com.project.managelesson.presentation.dashboard.components.SubjectCard
import com.project.managelesson.presentation.common_components.lessonList
import com.project.managelesson.presentation.common_components.taskList
import com.project.managelesson.tasks
import com.project.managelesson.test
import com.project.managelesson.utils.Screen

@Composable
fun DashboardScreen(
    navController: NavController
) {

    var addDialogState by rememberSaveable {
        mutableStateOf(false)
    }
    var deleteDialogState by rememberSaveable {
        mutableStateOf(false)
    }

    var subjectName by remember {
        mutableStateOf("")
    }

    var goalHours by remember {
        mutableStateOf("")
    }

    var selectedColor by remember {
        mutableStateOf(Subject.subjectColor.random())
    }

    AddDialog(
        onClickConfirmButton = { addDialogState = false },
        onDismissRequest = { addDialogState = false },
        title = "Add Subject",
        isOpen = addDialogState,
        subjectName = subjectName,
        goalHours = goalHours,
        onChangeName = { subjectName = it },
        onChangeGoalHours = { goalHours = it },
        selectedColor = selectedColor,
        onChangeColor = { selectedColor = it }
    )

    DeleteDialog(
        onClickConfirmButton = { deleteDialogState = false },
        onDismissRequest = { deleteDialogState = false },
        title = "Delete Lesson",
        text = "Do you want to delete lesson",
        isOpen = deleteDialogState
    )

    Scaffold(
        topBar = {
            DashboardTopBar()
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            item {
                CountCardSection(
                    subjectHours = 15,
                    studiedHours = "15",
                    goalHours = "15",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                )
            }
            item {
                SubjectCardSection(
                    subjectList = test,
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
                taskList = tasks,
                text = "You dont have any task",
                onClickCard = { taskId ->
                    val subjectId = null
                    navController.navigate("${Screen.TaskScreen.route}?taskId=${taskId}&subjectId={$subjectId}")
                },
                onClickCheckBox = { }
            )
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
            lessonList(
                title = "Recent lessons",
                lessonList = lessons,
                text = "You dont have any lesson",
                onClickDelete = { deleteDialogState = true }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Manage Lesson",
                style = MaterialTheme.typography.titleLarge
            )
        }
    )
}

@Composable
private fun CountCardSection(
    modifier: Modifier = Modifier,
    subjectHours: Int,
    studiedHours: String,
    goalHours: String
) {
    Row(
        modifier = modifier
    ) {
        CountCard(
            modifier = Modifier.weight(1f),
            title = "Subject count",
            count = "$subjectHours"
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
                    color = it.color,
                    onClick = { onSubjectClick(it.id) }
                )
            }
        }
    }
}