package com.project.managelesson.domain.use_case

import com.project.managelesson.domain.model.InvalidSubjectException
import com.project.managelesson.domain.model.Subject
import com.project.managelesson.domain.repository.SubjectRepository

class UpsertSubjectUseCase(
    private val subjectRepository: SubjectRepository
) {
    @Throws(InvalidSubjectException::class)
    suspend operator fun invoke(subject: Subject) {
        val error: String? = when {
            subject.title.isEmpty() -> "Title is empty"
            subject.title.length < 3 -> "Few letters. Write more letters"
            subject.title.length > 15 -> "Many letters. Write less letters"
            subject.goalHours < 1.0 -> "Goal hours can not be less 1"
            subject.goalHours > 1000 -> "Goal hours can not be 1000"
            else -> null
        }
        error?.let {
            throw InvalidSubjectException(it)
        }
        subjectRepository.upsertSubject(subject)
    }
}