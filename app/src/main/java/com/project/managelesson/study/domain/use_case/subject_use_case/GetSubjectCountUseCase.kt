package com.project.managelesson.study.domain.use_case.subject_use_case

import com.project.managelesson.study.domain.repository.SubjectRepository
import kotlinx.coroutines.flow.Flow

class GetSubjectCountUseCase(
    private val subjectRepository: SubjectRepository
) {
    operator fun invoke(): Flow<Int> {
        return subjectRepository.getSubjectCount()
    }
}