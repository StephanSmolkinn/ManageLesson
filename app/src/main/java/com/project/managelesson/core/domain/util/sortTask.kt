package com.project.managelesson.core.domain.util

import com.project.managelesson.study.domain.model.Task

fun sortTask(tasks: List<Task>): List<Task> {
    return tasks.sortedWith(compareBy<Task> { it.date }.thenByDescending { it.priority })
}