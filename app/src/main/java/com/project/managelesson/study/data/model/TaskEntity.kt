package com.project.managelesson.study.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = SubjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["subjectId"]
        )
    ], tableName = "task"
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val description: String,
    val priority: Int,
    val date: Long,
    val referToSubject: String,
    val isCompleted: Boolean,
    val subjectId: Int
)

class InvalidTaskException(message: String) : Exception(message)
