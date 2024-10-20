package com.project.managelesson.domain.util

import com.project.managelesson.domain.model.Task

fun sortTask(tasks: List<Task>): List<Task> {
    return tasks.sortedWith(compareBy<Task> { it.date }.thenByDescending { it.priority })
}