package com.project.managelesson.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [
    ForeignKey(entity = Subject::class, parentColumns = ["id"], childColumns = ["subjectId"])
])
data class Task(
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
