package com.project.managelesson.domain.use_case.subject_use_case

import com.project.managelesson.domain.repository.SubjectRepository
import kotlinx.coroutines.flow.Flow

class GetSubjectHoursUseCase(
    private val subjectRepository: SubjectRepository
) {
    operator fun invoke(): Flow<Double> {
        return subjectRepository.getSubjectHours()
    }
}