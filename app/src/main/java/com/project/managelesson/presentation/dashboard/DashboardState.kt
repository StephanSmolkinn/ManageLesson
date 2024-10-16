package com.project.managelesson.presentation.dashboard

import androidx.compose.ui.graphics.Color
import com.project.managelesson.domain.model.Lesson
import com.project.managelesson.domain.model.Subject

data class DashboardState(
    val totalSubjectCount: Int = 0,
    val totalLessonHours: Double = 0.0,
    val totalLessonGoalHours: Double = 0.0,
    val subjects: List<Subject> = emptyList(),
    val subjectName: String = "",
    val goalLessonHours: String = "",
    val subjectCardColor: List<Color> = Subject.subjectColor.random(),
    val lesson: Lesson? = null
)
