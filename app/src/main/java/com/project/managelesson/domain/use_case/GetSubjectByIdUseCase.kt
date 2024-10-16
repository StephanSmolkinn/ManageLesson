package com.project.managelesson.domain.use_case

import com.project.managelesson.domain.model.Subject
import com.project.managelesson.domain.repository.SubjectRepository

class GetSubjectByIdUseCase(
    private val subjectRepository: SubjectRepository
) {
    suspend operator fun invoke(subjectId: Int): Subject? {
        return subjectRepository.getSubjectById(subjectId)
    }
}