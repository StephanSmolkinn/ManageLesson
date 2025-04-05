package com.project.managelesson.study.presentation.lesson

import com.project.managelesson.study.domain.model.Lesson
import com.project.managelesson.study.domain.model.Subject

data class LessonState(
    val lessonEntity: Lesson? = null,
    val subjectId: Int? = null,
    val lessonEntityList: List<Lesson> = emptyList(),
    val subjectList: List<Subject> = emptyList(),
    val relateSubject: String? = null,
)