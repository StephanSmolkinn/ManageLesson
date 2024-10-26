package com.project.managelesson.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.project.managelesson.presentation.dashboard.DashboardScreen
import com.project.managelesson.presentation.lesson.LessonScreen
import com.project.managelesson.presentation.subject.SubjectScreen
import com.project.managelesson.presentation.task.TaskScreen
import com.project.managelesson.utils.Screen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.DashboardScreen.route
    ) {
        composable(route = Screen.DashboardScreen.route) {
            DashboardScreen(navController = navController)
        }
        composable(
            route = Screen.SubjectScreen.route + "?subjectId={subjectId}",
            arguments = listOf(
                navArgument(
                    name = "subjectId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            SubjectScreen(
                navController = navController,
                subjectId = it.arguments?.getInt("subjectId") ?: -1
            )
        }
        composable(
            route = Screen.TaskScreen.route + "?taskId={taskId}&taskSubjectId={taskSubjectId}",
            arguments = listOf(
                navArgument(
                    name = "taskId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument(
                    name = "taskSubjectId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            TaskScreen(
                navController = navController,
                subjectId = it.arguments?.getInt("taskSubjectId") ?: -1,
                taskId = it.arguments?.getInt("taskId") ?: -1
            )
        }
        composable(route = Screen.LessonScreen.route) {
            LessonScreen(navController = navController)
        }
    }
}