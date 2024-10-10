package com.project.managelesson.domain.use_case

import com.project.managelesson.domain.model.Subject
import com.project.managelesson.domain.repository.SubjectRepository

class UpsertSubjectUseCase(
    private val subjectRepository: SubjectRepository
) {
    suspend operator fun invoke(subject: Subject) {
        subjectRepository.upsertSubject(subject)
    }
}