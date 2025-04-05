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
    ], tableName = "lesson"
)
data class LessonEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val date: Long,
    val duration: Long,
    val relateSubject: String,
    val subjectId: Int
)
