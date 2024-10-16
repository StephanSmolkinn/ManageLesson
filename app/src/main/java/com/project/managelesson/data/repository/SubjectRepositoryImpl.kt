package com.project.managelesson.data.repository

import com.project.managelesson.data.data_source.SubjectDao
import com.project.managelesson.domain.model.Subject
import com.project.managelesson.domain.repository.SubjectRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubjectRepositoryImpl @Inject constructor(
    private val subjectDao: SubjectDao
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
        TODO("Not yet implemented")
    }

    override suspend fun deleteSubject(subjectId: Int) {
        TODO("Not yet implemented")
    }

    override fun getAllSubject(): Flow<List<Subject>> {
        return subjectDao.getAllSubject()
    }
}