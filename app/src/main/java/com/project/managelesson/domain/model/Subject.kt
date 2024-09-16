package com.project.managelesson.domain.model

import androidx.compose.ui.graphics.Color
import com.project.managelesson.presentation.theme.gradient1
import com.project.managelesson.presentation.theme.gradient2
import com.project.managelesson.presentation.theme.gradient3
import com.project.managelesson.presentation.theme.gradient4
import com.project.managelesson.presentation.theme.gradient5

data class Subject(
    val id: Int,
    val title: String,
    val goalHours: Double,
    val color: List<Color>
) {
    companion object {
        val subjectColor = listOf(gradient1, gradient2, gradient3, gradient4, gradient5)
    }
}
