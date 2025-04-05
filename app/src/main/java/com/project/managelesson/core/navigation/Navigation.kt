package com.project.managelesson.core.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavDeepLink
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.project.managelesson.auth.presentation.profile.ProfileScreen
import com.project.managelesson.auth.presentation.sign_in.SignInScreen
import com.project.managelesson.auth.presentation.sign_up.SignUpScreen
import com.project.managelesson.study.presentation.dashboard.DashboardScreen
import com.project.managelesson.study.presentation.lesson.LessonScreen
import com.project.managelesson.study.presentation.lesson.LessonTimerService
import com.project.managelesson.study.presentation.subject.SubjectScreen
import com.project.managelesson.study.presentation.task.TaskScreen

@SuppressLint("RestrictedApi")
@Composable
fun Navigation(
    timerService: LessonTimerService
) {
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
        composable(
            route = Screen.LessonScreen.route,
            deepLinks = listOf(
                NavDeepLink(uri = "managelesson://dashboard/lesson")
            )
        ) {
            LessonScreen(
                navController = navController,
                timerService = timerService
            )
        }
        composable(
            route = Screen.SignUpScreen.route
        ) {
            SignUpScreen(navController = navController)
        }
        composable(
            route = Screen.SignInScreen.route
        ) {
            SignInScreen(navController = navController)
        }
        composable(
            route = Screen.ProfileScreen.route
        ) {
            ProfileScreen(navController = navController)
        }
    }
}