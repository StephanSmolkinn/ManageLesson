package com.project.managelesson.data.repository

import com.project.managelesson.data.data_source.local.LessonDao
import com.project.managelesson.domain.model.Lesson
import com.project.managelesson.domain.repository.LessonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LessonRepositoryImpl @Inject constructor(
    private val lessonDao: LessonDao
): LessonRepository {
    override suspend fun insertLesson(lesson: Lesson) {
        lessonDao.insertLesson(lesson)
    }

    override suspend fun deleteLesson(lesson: Lesson) {
        lessonDao.deleteLesson(lesson)
    }

    override fun getRecentTenLessonBySubject(subjectId: Int): Flow<List<Lesson>> {
        return lessonDao.getRecentLessonBySubjectId(subjectId)
    }

    override fun getRecentFiveLesson(): Flow<List<Lesson>> {
        return lessonDao.getAllLesson()
    }

    override fun getAllLesson(): Flow<List<Lesson>> {
        return lessonDao.getAllLesson()
    }

    override fun getSumDuration(): Flow<Long> {
        return lessonDao.getSumDuration()
    }

    override fun getSumDurationBySubjectId(subjectId: Int): Flow<Long> {
        return lessonDao.getSumDurationBySubjectId(subjectId)
    }
}