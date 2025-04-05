package com.project.managelesson.auth.presentation.profile.components

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import com.project.managelesson.study.domain.model.Task


fun LazyListScope.taskCardProfileList(list: List<Task>) {
    items(list) {
        TaskCardProfile(
            name = it.title,
            description = it.description,
            priority = it.priority
        )
    }
}