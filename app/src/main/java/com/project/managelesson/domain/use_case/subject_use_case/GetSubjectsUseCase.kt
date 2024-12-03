package com.project.managelesson.domain.use_case.subject_use_case

import com.project.managelesson.domain.model.Subject
import com.project.managelesson.domain.repository.SubjectRepository
import kotlinx.coroutines.flow.Flow

class GetSubjectsUseCase(
    private val subjectRepository: SubjectRepository
) {
    operator fun invoke(): Flow<List<Subject>> {
        return subjectRepository.getAllSubject()
    }
}