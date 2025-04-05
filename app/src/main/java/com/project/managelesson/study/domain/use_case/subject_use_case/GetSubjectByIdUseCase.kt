package com.project.managelesson.study.domain.use_case.subject_use_case

import com.project.managelesson.study.domain.model.Subject
import com.project.managelesson.study.domain.repository.SubjectRepository

class GetSubjectByIdUseCase(
    private val subjectRepository: SubjectRepository
) {
    suspend operator fun invoke(subjectId: Int): Subject? {
        return subjectRepository.getSubjectById(subjectId)
    }
}