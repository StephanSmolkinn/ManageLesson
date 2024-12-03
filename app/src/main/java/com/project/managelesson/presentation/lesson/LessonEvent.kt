package com.project.managelesson.presentation.lesson

import com.project.managelesson.domain.model.Lesson
import com.project.managelesson.domain.model.Subject

sealed class LessonEvent {
    data class OnRelateSubject(val subject: Subject) : LessonEvent()
    data class SaveLesson(val duration: Long) : LessonEvent()
    data class OnDeleteLesson(val lesson: Lesson) : LessonEvent()
    data class UpdateSubject(
        val relateSubject: String?,
        val id: Int?
    ) : LessonEvent()
    data object DeleteLesson : LessonEvent()
    data object SubjectId : LessonEvent()
}