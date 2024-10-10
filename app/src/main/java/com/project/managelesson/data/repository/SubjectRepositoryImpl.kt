package com.project.managelesson.data.repository

import com.project.managelesson.data.data_source.SubjectDao
import com.project.managelesson.domain.model.Subject
import com.project.managelesson.domain.repository.SubjectRepository
import kotlinx.coroutines.flow.Flow

class SubjectRepositoryImpl(
    private val subjectDao: SubjectDao
): SubjectRepository {
    override suspend fun upsertSubject(subject: Subject) {
        subjectDao.upsertSubject(subject)
    }

    override fun getSubjectCount(): Flow<Int> {
        TODO("Not yet implemented")
    }

    override fun getSubjectHours(): Flow<Double> {
        TODO("Not yet implemented")
    }

    override suspend fun getSubjectById(subjectId: Int): Subject? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSubject(subjectId: Int) {
        TODO("Not yet implemented")
    }

    override fun getAllSubject(): Flow<List<Subject>> {
        TODO("Not yet implemented")
    }
}