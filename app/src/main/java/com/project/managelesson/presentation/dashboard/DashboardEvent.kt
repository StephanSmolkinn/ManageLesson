package com.project.managelesson.presentation.dashboard

import androidx.compose.ui.graphics.Color
import com.project.managelesson.domain.model.Lesson
import com.project.managelesson.domain.model.Task

sealed class DashboardEvent {
    data object SaveSubject : DashboardEvent()
    data object DeleteLesson : DashboardEvent()
    data class OnDeleteLesson(val lesson: Lesson) : DashboardEvent()
    data class OnTaskChange(val task: Task) : DashboardEvent()
    data class OnSubjectCardColorChange(val colors: List<Color>) : DashboardEvent()
    data class OnSubjectNameChange(val name: String) : DashboardEvent()
    data class OnSubjectHoursChange(val hours: String) : DashboardEvent()
}