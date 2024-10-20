package com.project.managelesson.domain.use_case

import com.project.managelesson.domain.repository.LessonRepository
import kotlinx.coroutines.flow.Flow

class GetSumDurationBySubjectUseCase(
    private val lessonRepository: LessonRepository
) {
    operator fun invoke(subjectId: Int): Flow<Long> {
        return lessonRepository.getSumDurationBySubjectId(subjectId)
    }
}