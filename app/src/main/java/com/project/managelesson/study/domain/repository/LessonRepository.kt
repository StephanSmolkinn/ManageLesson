package com.project.managelesson.study.domain.repository

import com.project.managelesson.study.domain.model.Lesson
import kotlinx.coroutines.flow.Flow

interface LessonRepository {
    suspend fun insertLesson(lesson: Lesson)

    suspend fun deleteLesson(lesson: Lesson)

    fun getRecentTenLessonBySubject(subjectId: Int): Flow<List<Lesson>>

    fun getRecentFiveLesson(): Flow<List<Lesson>>

    fun getAllLesson(): Flow<List<Lesson>>

    fun getSumDuration(): Flow<Long>

    fun getSumDurationBySubjectId(subjectId: Int): Flow<Long>
}