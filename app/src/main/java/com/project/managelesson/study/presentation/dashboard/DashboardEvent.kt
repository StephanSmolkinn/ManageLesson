package com.project.managelesson.study.presentation.dashboard

import androidx.compose.ui.graphics.Color
import com.project.managelesson.study.domain.model.Lesson
import com.project.managelesson.study.domain.model.Task

sealed class DashboardEvent {
    data object SaveSubject : DashboardEvent()
    data object DeleteLesson : DashboardEvent()
    data class OnDeleteLesson(val lessonEntity: Lesson) : DashboardEvent()
    data class OnTaskChange(val task: Task) : DashboardEvent()
    data class OnSubjectCardColorChange(val colors: List<Color>) : DashboardEvent()
    data class OnSubjectNameChange(val name: String) : DashboardEvent()
    data class OnSubjectHoursChange(val hours: String) : DashboardEvent()
}