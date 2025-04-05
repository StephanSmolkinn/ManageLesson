package com.project.managelesson.auth.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.project.managelesson.R
import com.project.managelesson.core.navigation.Screen
import com.project.managelesson.core.presentation.common_components.TaskCard
import com.project.managelesson.core.presentation.theme.primaryDark
import com.project.managelesson.study.presentation.dashboard.components.EmptyListSection
import com.project.managelesson.study.presentation.task.components.TaskDatePickerDialog
import java.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    val density = LocalDensity.current
    val visible = remember { mutableStateOf(true) }
    val taskDatePicker = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )
    val isOpenDatePicker = remember { mutableStateOf(false) }

    TaskDatePickerDialog(
        isOpen = isOpenDatePicker.value,
        state = taskDatePicker,
        onClickDismissRequest = {
            isOpenDatePicker.value = false
        },
        onClickConfirmButton = {
            viewModel.onEvent(ProfileEvent.OnDateChange(taskDatePicker.selectedDateMillis))
            isOpenDatePicker.value = false
        }
    )


    Scaffold(
        topBar = {
            ProfileTopBar(
                onBackClick = {
                    navController.navigateUp()
                },
                onLogOut = {
                    viewModel.onEvent(ProfileEvent.OnLogout)
                },
                name = state.value.user?.fullName ?: ""
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 32.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = "You will receive notifications about tasks",
                    style = MaterialTheme.typography.bodyLarge
                )
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    tint = Color.Green
                )
            }

            Surface(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(
                    topStart = 32.dp,
                    topEnd = 32.dp
                ),
                color = primaryDark
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Button(
                            onClick = {
                                isOpenDatePicker.value = true
                            }
                        ) {
                            Text(
                                text = "Select date",
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center,
                                color = Color.Black
                            )
                        }
                        Spacer(Modifier.height(16.dp))
                    }
                    if (state.value.tasksByDate.isEmpty()) {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(bottom = 16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                EmptyListSection(
                                    modifier = Modifier
                                        .size(120.dp),
                                    text = "You don`t have any tasks in selected day",
                                    image = R.drawable.task
                                )
                            }
                        }
                    } else {
                        items(
                            items = state.value.tasksByDate,
                            key = { it.id!! }
                        ) {
                            TaskCard(
                                modifier = Modifier
                                    .padding(horizontal = 12.dp, vertical = 4.dp),
                                task = it,
                                onClickCheckBox = {
                                    viewModel.onEvent(ProfileEvent.OnTaskChange(it))
                                },
                                onClick = {
                                    val subjectId = null
                                    navController.navigate("${Screen.TaskScreen.route}?taskId=${it.id}&taskSubjectId=${subjectId}")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar(
    onBackClick: () -> Unit,
    onLogOut: () -> Unit,
    name: String
) {
    CenterAlignedTopAppBar(
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
                text = name,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        actions = {
            IconButton(onClick = onLogOut) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = "Logout"
                )
            }
        }
    )
}
