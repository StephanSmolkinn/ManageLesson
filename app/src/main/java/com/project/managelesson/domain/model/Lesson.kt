package com.project.managelesson.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [
    ForeignKey(entity = Subject::class, parentColumns = ["id"], childColumns = ["subjectId"])
])
data class Lesson(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val date: Long,
    val duration: Long,
    val relateSubject: String,
    val subjectId: Int
)
