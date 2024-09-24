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
import com.project.managelesson.R
import com.project.managelesson.domain.model.Lesson
import com.project.managelesson.domain.model.Subject
import com.project.managelesson.domain.model.Task
import com.project.managelesson.presentation.common_components.AddDialog
import com.project.managelesson.presentation.dashboard.components.CountCard
import com.project.managelesson.presentation.dashboard.components.EmptyListSection
import com.project.managelesson.presentation.dashboard.components.SubjectCard
import com.project.managelesson.presentation.common_components.lessonList
import com.project.managelesson.presentation.common_components.taskList

@Composable
fun DashboardScreen() {

    val test = listOf(
        Subject(0, "Physics", 12.0, color = Subject.subjectColor[0],),
        Subject(0,"Maths", 12.6, color = Subject.subjectColor[1]),
        Subject(0,"English", 22.9, color = Subject.subjectColor[2]),
        Subject(0,"Geology", 9.0, color = Subject.subjectColor[3]),
        Subject(0,"Physics", 52.0, color = Subject.subjectColor[4])
    )

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

    var stateDialog by rememberSaveable {
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
        onClickConfirmButton = { stateDialog = false },
        onDismissRequest = { stateDialog = false },
        title = "Add Subject",
        isOpen = stateDialog,
        subjectName = subjectName,
        goalHours = goalHours,
        onChangeName = { subjectName = it },
        onChangeGoalHours = { goalHours = it },
        selectedColor = selectedColor,
        onChangeColor = { selectedColor = it }
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
                        stateDialog = true
                    }
                )
            }
            item {
                Button(
                    onClick = { },
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
                onClickCard = { },
                onClickCheckBox = { }
            )
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
            lessonList(
                title = "Recent lessons",
                lessonList = lessons,
                text = "You dont have any lesson",
                onClickDelete = { }
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
    onClick: () -> Unit
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
                    onClick = { }
                )
            }
        }
    }
}