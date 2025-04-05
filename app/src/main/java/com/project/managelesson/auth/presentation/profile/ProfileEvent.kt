package com.project.managelesson.auth.presentation.profile

import com.project.managelesson.study.domain.model.Task

sealed class ProfileEvent {
    data object OnLogout : ProfileEvent()
    data class OnTaskChange(val task: Task) : ProfileEvent()
    data class OnDateChange(val date: Long?) : ProfileEvent()
}
