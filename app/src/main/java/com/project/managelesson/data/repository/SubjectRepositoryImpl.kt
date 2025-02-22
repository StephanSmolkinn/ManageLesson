package com.project.managelesson.data.repository

import com.project.managelesson.data.data_source.local.LessonDao
import com.project.managelesson.data.data_source.local.SubjectDao
import com.project.managelesson.data.data_source.local.TaskDao
import com.project.managelesson.domain.model.Subject
import com.project.managelesson.domain.repository.SubjectRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubjectRepositoryImpl @Inject constructor(
    private val subjectDao: SubjectDao,
    private val taskDao: TaskDao,
    private val lessonDao: LessonDao
): SubjectRepository {
    override suspend fun upsertSubject(subject: Subject) {
        subjectDao.upsertSubject(subject)
    }

    override fun getSubjectCount(): Flow<Int> {
        return subjectDao.getSubjectCount()
    }

    override fun getSubjectHours(): Flow<Double> {
        return subjectDao.getSubjectHours()
    }

    override suspend fun getSubjectById(subjectId: Int): Subject? {
        return subjectDao.getSubjectById(subjectId)
    }

    override suspend fun deleteSubject(subjectId: Int) {
        taskDao.deleteTaskBySubjectId(subjectId)
        lessonDao.deleteLessonBySubjectId(subjectId)
        subjectDao.deleteSubject(subjectId)
    }

    override fun getAllSubject(): Flow<List<Subject>> {
        return subjectDao.getAllSubject()
    }
}