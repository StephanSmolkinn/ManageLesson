package com.project.managelesson.study.domain.model

data class Task(
    val id: Int? = null,
    val title: String,
    val description: String,
    val priority: Int,
    val date: Long,
    val referToSubject: String,
    val isCompleted: Boolean,
    val subjectId: Int
)
