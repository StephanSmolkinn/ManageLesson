package com.project.managelesson.study.domain.use_case.subject_use_case

import com.project.managelesson.study.domain.model.Subject
import com.project.managelesson.study.domain.repository.SubjectRepository
import kotlinx.coroutines.flow.Flow

class GetSubjectsUseCase(
    private val subjectRepository: SubjectRepository
) {
    operator fun invoke(): Flow<List<Subject>> {
        return subjectRepository.getAllSubject()
    }
}