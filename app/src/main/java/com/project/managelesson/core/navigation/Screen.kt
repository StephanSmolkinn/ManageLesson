package com.project.managelesson.core.navigation

sealed class Screen(val route: String) {
    object DashboardScreen : Screen(route = "dashboard_screen")
    object SubjectScreen : Screen(route = "subject_screen")
    object TaskScreen : Screen(route = "task_screen")
    object LessonScreen : Screen(route = "lesson_screen")
    object SignUpScreen : Screen(route = "sign_up_screen")
    object SignInScreen : Screen(route = "sign_in_screen")
    object ProfileScreen : Screen(route = "profile_screen")
}