package com.project.managelesson.study.presentation.subject

import androidx.compose.ui.graphics.Color
import com.project.managelesson.study.domain.model.Lesson
import com.project.managelesson.study.domain.model.Subject
import com.project.managelesson.study.domain.model.Task

data class SubjectState(
    val subjectId: Int? = null,
    val subjectName: String = "",
    val goalLessonHours: String = "",
    val studyHours: Double = 0.0,
    val cardColors: List<Color> = Subject.subjectColor.random(),
    val upcomingTaskList: List<Task> = emptyList(),
    val completedTaskList: List<Task> = emptyList(),
    val recentLessonEntityList: List<Lesson> = emptyList(),
    val lessonEntity: Lesson? = null,
    val progress: Double = 0.0,
    val isLoading: Boolean = false
)
