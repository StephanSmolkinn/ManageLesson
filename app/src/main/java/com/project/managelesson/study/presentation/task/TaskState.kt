package com.project.managelesson.study.presentation.task

import com.project.managelesson.core.utils.Priority
import com.project.managelesson.study.domain.model.Subject

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