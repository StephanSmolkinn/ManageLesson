package com.project.managelesson.study.domain.model

data class Lesson(
    val id: Int? = null,
    val date: Long,
    val duration: Long,
    val relateSubject: String,
    val subjectId: Int
)
