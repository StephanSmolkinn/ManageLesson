package com.project.managelesson.study.presentation.lesson

import com.project.managelesson.study.domain.model.Lesson
import com.project.managelesson.study.domain.model.Subject

sealed class LessonEvent {
    data class OnRelateSubject(val subject: Subject) : LessonEvent()
    data class SaveLesson(val duration: Long) : LessonEvent()
    data class OnDeleteLesson(val lessonEntity: Lesson) : LessonEvent()
    data class UpdateSubject(
        val relateSubject: String?,
        val id: Int?
    ) : LessonEvent()
    data object DeleteLesson : LessonEvent()
    data object SubjectId : LessonEvent()
}