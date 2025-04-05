package com.project.managelesson.study.presentation.task

import com.project.managelesson.core.utils.Priority
import com.project.managelesson.study.domain.model.Subject

sealed class TaskEvent {
    data object OnChangeComplete : TaskEvent()
    data object SaveTask : TaskEvent()
    data object DeleteTask : TaskEvent()
    data class OnTaskNameChange(val name: String) : TaskEvent()
    data class OnTaskDescriptionChange(val description: String) : TaskEvent()
    data class OnDateChange(val date: Long?) : TaskEvent()
    data class OnPriorityChange(val priority: Priority) : TaskEvent()
    data class OnRelateSubject(val subject: Subject) : TaskEvent()
}