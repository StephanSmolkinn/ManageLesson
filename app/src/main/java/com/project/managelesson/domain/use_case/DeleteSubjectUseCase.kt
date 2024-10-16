package com.project.managelesson.domain.use_case

import com.project.managelesson.domain.repository.SubjectRepository

class DeleteSubjectUseCase(
    private val subjectRepository: SubjectRepository
) {
    suspend operator fun invoke(subjectId: Int) {
        subjectRepository.deleteSubject(subjectId)
    }
}