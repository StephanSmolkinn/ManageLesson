package com.project.managelesson.presentation.task

import com.project.managelesson.domain.model.Subject
import com.project.managelesson.utils.Priority

data class TaskState(
    val taskId: Int? = null,
    val taskName: String = "",
    val description: String = "",
    val date: Long? = null,
    val isTaskComplete: Boolean = false,
    val priority: Priority = Priority.LOW,
    val relateSubject: String? = null,
    val subjectList: List<Subject> = emptyList(),
    val subjectId: Int? = null
)