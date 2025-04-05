package com.project.managelesson.study.data.repository

import com.project.managelesson.study.data.data_source.local.LessonDao
import com.project.managelesson.study.data.data_source.local.SubjectDao
import com.project.managelesson.study.data.data_source.local.TaskDao
import com.project.managelesson.study.data.mappers.toDomainSubject
import com.project.managelesson.study.data.mappers.toSubject
import com.project.managelesson.study.domain.model.Subject
import com.project.managelesson.study.domain.repository.SubjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SubjectRepositoryImpl @Inject constructor(
    private val subjectDao: SubjectDao,
    private val taskDao: TaskDao,
    private val lessonDao: LessonDao
): SubjectRepository {
    override suspend fun upsertSubject(subject: Subject) {
        subjectDao.upsertSubject(subject.toSubject())
    }

    override fun getSubjectCount(): Flow<Int> {
        return subjectDao.getSubjectCount()
    }

    override fun getSubjectHours(): Flow<Double> {
        return subjectDao.getSubjectHours()
    }

    override suspend fun getSubjectById(subjectId: Int): Subject? {
        return subjectDao.getSubjectById(subjectId)?.let {
            Subject(
                id = it.id,
                title = it.title,
                goalHours = it.goalHours,
                color = it.color
            )
        }
    }

    override suspend fun deleteSubject(subjectId: Int) {
        taskDao.deleteTaskBySubjectId(subjectId)
        lessonDao.deleteLessonBySubjectId(subjectId)
        subjectDao.deleteSubject(subjectId)
    }

    override fun getAllSubject(): Flow<List<Subject>> {
        val list = subjectDao.getAllSubject().map {
            it.map {
                it.toDomainSubject()
            }
        }
        return list
    }
}