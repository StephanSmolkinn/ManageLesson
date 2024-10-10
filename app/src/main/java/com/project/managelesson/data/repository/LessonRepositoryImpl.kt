package com.project.managelesson.data.repository

import com.project.managelesson.data.data_source.LessonDao
import com.project.managelesson.domain.model.Lesson
import com.project.managelesson.domain.repository.LessonRepository
import kotlinx.coroutines.flow.Flow

class LessonRepositoryImpl(
    private val lessonDao: LessonDao
): LessonRepository {
    override suspend fun insertLesson(lesson: Lesson) {
        lessonDao.insertLesson(lesson)
    }

    override suspend fun deleteLesson(lesson: Lesson) {
        TODO("Not yet implemented")
    }

    override fun getRecentTenLessonBySubject(subjectId: Int): Flow<List<Lesson>> {
        TODO("Not yet implemented")
    }

    override fun getRecentFiveLesson(): Flow<List<Lesson>> {
        TODO("Not yet implemented")
    }

    override fun getAllLesson(): Flow<List<Lesson>> {
        TODO("Not yet implemented")
    }

    override fun getSumDuration(): Flow<Long> {
        TODO("Not yet implemented")
    }

    override fun getSumDurationBySubjectId(subjectId: Int): Flow<Long> {
        TODO("Not yet implemented")
    }
}