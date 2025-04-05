package com.project.managelesson.study.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.project.managelesson.core.presentation.theme.gradient1
import com.project.managelesson.core.presentation.theme.gradient2
import com.project.managelesson.core.presentation.theme.gradient3
import com.project.managelesson.core.presentation.theme.gradient4
import com.project.managelesson.core.presentation.theme.gradient5

@Entity(tableName = "subject")
data class SubjectEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val goalHours: Double,
    val color: List<Int>
) {
    companion object {
        val subjectColor = listOf(gradient1, gradient2, gradient3, gradient4, gradient5)
    }
}

class InvalidSubjectException(message: String) : Exception(message)
