package com.project.managelesson.utils

sealed class Screen(val route: String) {
    object DashboardScreen : Screen(route = "dashboard_screen")
    object SubjectScreen : Screen(route = "subject_screen")
    object TaskScreen : Screen(route = "task_screen")
    object LessonScreen : Screen(route = "lesson_screen")
}