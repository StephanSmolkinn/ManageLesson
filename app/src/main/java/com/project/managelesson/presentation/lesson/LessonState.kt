package com.project.managelesson.presentation.lesson

import com.project.managelesson.domain.model.Lesson
import com.project.managelesson.domain.model.Subject

data class LessonState(
    val lesson: Lesson? = null,
    val subjectId: Int? = null,
    val lessonList: List<Lesson> = emptyList(),
    val subjectList: List<Subject> = emptyList(),
    val relateSubject: String? = null,
)