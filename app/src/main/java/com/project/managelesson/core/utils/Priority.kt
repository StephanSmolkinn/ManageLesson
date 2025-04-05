package com.project.managelesson.core.utils

import androidx.compose.ui.graphics.Color

enum class Priority(val title: String, val value: Int, val color: Color) {
    LOW(title = "Low", value = 0, color = Color.Green),
    MEDIUM(title = "Medium", value = 1, color = Color.Yellow),
    HIGH(title = "High", value = 2, color = Color.Red);

    companion object {
        fun takePriority(value: Int) = entries.firstOrNull { it.value == value } ?: MEDIUM
    }
}