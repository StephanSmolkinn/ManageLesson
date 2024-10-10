package com.project.managelesson.domain.repository

import androidx.room.Query
import com.project.managelesson.domain.model.Subject
import kotlinx.coroutines.flow.Flow

interface SubjectRepository {
    suspend fun upsertSubject(subject: Subject)

    fun getSubjectCount(): Flow<Int>

    fun getSubjectHours(): Flow<Double>

    suspend fun getSubjectById(subjectId: Int): Subject?

    suspend fun deleteSubject(subjectId: Int)

    fun getAllSubject(): Flow<List<Subject>>
}