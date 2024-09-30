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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.project.managelesson.domain.model.Lesson
import com.project.managelesson.domain.model.Subject
import com.project.managelesson.domain.model.Task
import com.project.managelesson.presentation.common_components.AddDialog
import com.project.managelesson.presentation.common_components.DeleteDialog
import com.project.managelesson.presentation.dashboard.components.CountCard
import com.project.managelesson.presentation.common_components.lessonList
import com.project.managelesson.presentation.common_components.taskList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectScreen(

) {

    val stateList = rememberLazyListState()
    val stateFabExpanded = remember {
        derivedStateOf { stateList.firstVisibleItemIndex == 0 }
    }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val tasks = listOf(
        Task(1, title = "Task23232", "Do something good", 0, 0L, "", true, 0),
        Task(1,title = "Task4343", "Do something good", 1, 0L, "", false, 0),
        Task(1,title = "Task", "Do something good", 1, 0L, "", true, 0),
        Task(1,title = "Task", "Do something good", 2, 0L, "", false, 0),
        Task(1,title = "Task435454", "Do something good", 0, 0L, "", true, 0),
        Task(1,title = "Task", "Do something good", 2, 0L, "", true, 0),
        Task(1,title = "Task5543", "Do something good", 0, 0L, "", true, 0),
        Task(1, title = "Task", "Do something good", 2, 0L, "", true, 0)
    )

    val lessons = listOf(
        Lesson(1, 0L, 2L, "Physics", 0),
        Lesson(1, 0L, 0L, "Maths", 0),
        Lesson(1, 0L, 3L, "Physics", 0),
        Lesson(1, 0L, 0L, "Geology", 0),
        Lesson(1, 0L, 2L, "Physics", 0),
        Lesson(1, 0L, 5L, "English", 0)
    )

    var addDialogState by rememberSaveable {
        mutableStateOf(false)
    }
    var deleteLessonDialogState by rememberSaveable {
        mutableStateOf(false)
    }
    var deleteSubjectDialogState by rememberSaveable {
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
        onClickConfirmButton = { deleteLessonDialogState = false },
        onDismissRequest = { deleteLessonDialogState = false },
        title = "Delete Lesson",
        text = "Do you want to delete lesson",
        isOpen = deleteLessonDialogState
    )

    DeleteDialog(
        onClickConfirmButton = { deleteSubjectDialogState = false },
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
                title = "English",
                onBackClick = { /*TODO*/ },
                onDeleteClick = { deleteSubjectDialogState = true },
                onEditClick = { addDialogState = true }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { },
                icon = {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                },
                text = { Text(text = "Add task") },
                shape = CircleShape,
                expanded = stateFabExpanded.value
            ) 
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
                    goalHours = "10",
                    studiedHours = "5",
                    progressStudy = 0.12,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                )
            }
            taskList(
                title = "Upcoming tasks",
                taskList = emptyList(),
                text = "You dont have any task",
                onClickCard = { },
                onClickCheckBox = { }
            )
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
            taskList(
                title = "Completed tasks",
                taskList = emptyList(),
                text = "You dont have any completed task",
                onClickCard = { },
                onClickCheckBox = { }
            )
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
            lessonList(
                title = "Recent lessons",
                lessonList = emptyList(),
                text = "You dont have any lesson",
                onClickDelete = { deleteLessonDialogState = true }
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
    progressStudy: Double
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
            title = "Study hours",
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
            )
            Text(text = "$progressState%")
        }
    }
}