package com.project.managelesson.study.presentation.subject

import androidx.compose.ui.graphics.Color
import com.project.managelesson.study.domain.model.Lesson
import com.project.managelesson.study.domain.model.Task

sealed class SubjectEvent {
    data object UpdateSubject : SubjectEvent()
    data object UpdateProgress : SubjectEvent()
    data object DeleteSubject : SubjectEvent()
    data object DeleteLesson : SubjectEvent()
    data class OnTaskCompleteChange(val task: Task) : SubjectEvent()
    data class OnSubjectCardColorChange(val color: List<Color>) : SubjectEvent()
    data class OnSubjectNameChange(val name: String) : SubjectEvent()
    data class OnSubjectGoalHourChange(val hours: String) : SubjectEvent()
    data class OnDeleteLesson(val lessonEntity: Lesson) : SubjectEvent()
}