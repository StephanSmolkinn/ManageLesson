package com.project.managelesson.domain.model

data class Lesson(
    val id: Int,
    val date: Long,
    val duration: Long,
    val relateSubject: String,
    val subjectId: Int
)
