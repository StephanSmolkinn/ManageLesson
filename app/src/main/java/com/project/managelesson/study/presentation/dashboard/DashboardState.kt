package com.project.managelesson.study.presentation.dashboard

import androidx.compose.ui.graphics.Color
import com.project.managelesson.study.domain.model.Lesson
import com.project.managelesson.study.domain.model.Subject

data class DashboardState(
    val totalSubjectCount: Int = 0,
    val totalLessonHours: Double = 0.0,
    val totalLessonGoalHours: Double = 0.0,
    val subjects: List<Subject> = emptyList(),
    val subjectName: String = "",
    val goalLessonHours: String = "",
    val subjectCardColor: List<Color> = Subject.subjectColor.random(),
    val lessonEntity: Lesson? = null
)
