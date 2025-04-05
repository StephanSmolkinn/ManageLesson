package com.project.managelesson.study.data.repository

import com.project.managelesson.study.data.data_source.local.LessonDao
import com.project.managelesson.study.data.mappers.toDomainLesson
import com.project.managelesson.study.data.mappers.toLesson
import com.project.managelesson.study.domain.model.Lesson
import com.project.managelesson.study.domain.repository.LessonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LessonRepositoryImpl @Inject constructor(
    private val lessonDao: LessonDao
): LessonRepository {
    override suspend fun insertLesson(lesson: Lesson) {
        lessonDao.insertLesson(lesson.toLesson())
    }

    override suspend fun deleteLesson(lesson: Lesson) {
        lessonDao.deleteLesson(lesson.toLesson())
    }

    override fun getRecentTenLessonBySubject(subjectId: Int): Flow<List<Lesson>> {
        return lessonDao.getRecentLessonBySubjectId(subjectId).map {
            it.map {
                it.toDomainLesson()
            }
        }
    }

    override fun getRecentFiveLesson(): Flow<List<Lesson>> {
        return lessonDao.getAllLesson().map {
            it.map {
                it.toDomainLesson()
            }
        }
    }

    override fun getAllLesson(): Flow<List<Lesson>> {
        return lessonDao.getAllLesson().map {
            it.map {
                it.toDomainLesson()
            }
        }
    }

    override fun getSumDuration(): Flow<Long> {
        return lessonDao.getSumDuration()
    }

    override fun getSumDurationBySubjectId(subjectId: Int): Flow<Long> {
        return lessonDao.getSumDurationBySubjectId(subjectId)
    }
}