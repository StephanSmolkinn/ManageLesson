package com.project.managelesson.domain.use_case

import com.project.managelesson.domain.repository.SubjectRepository
import kotlinx.coroutines.flow.Flow

class GetSubjectCountUseCase(
    private val subjectRepository: SubjectRepository
) {
    operator fun invoke(): Flow<Int> {
        return subjectRepository.getSubjectCount()
    }
}