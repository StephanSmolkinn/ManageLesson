package com.project.managelesson.study.domain.use_case.subject_use_case

import com.project.managelesson.study.domain.repository.SubjectRepository

class DeleteSubjectUseCase(
    private val subjectRepository: SubjectRepository
) {
    suspend operator fun invoke(subjectId: Int) {
        subjectRepository.deleteSubject(subjectId)
    }
}