package com.project.managelesson.study.domain.model

import com.project.managelesson.core.presentation.theme.gradient1
import com.project.managelesson.core.presentation.theme.gradient2
import com.project.managelesson.core.presentation.theme.gradient3
import com.project.managelesson.core.presentation.theme.gradient4
import com.project.managelesson.core.presentation.theme.gradient5

data class Subject(
    val id: Int? = null,
    val title: String,
    val goalHours: Double,
    val color: List<Int>
) {
    companion object {
        val subjectColor = listOf(gradient1, gradient2, gradient3, gradient4, gradient5)
    }
}