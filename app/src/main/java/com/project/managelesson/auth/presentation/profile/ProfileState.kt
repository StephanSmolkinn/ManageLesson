package com.project.managelesson.auth.presentation.profile

import com.project.managelesson.auth.domain.model.User
import com.project.managelesson.study.domain.model.Task
import java.time.Instant

data class ProfileState(
    val user: User? = null,
    val isLoading: Boolean = true,
    val tasks: List<Task> = emptyList(),
    val tasksByDate: List<Task> = emptyList(),
    val selectedDate: Long? = Instant.now().toEpochMilli()
)
