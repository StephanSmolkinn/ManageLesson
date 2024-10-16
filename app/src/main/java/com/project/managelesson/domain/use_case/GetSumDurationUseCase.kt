package com.project.managelesson.domain.use_case

import com.project.managelesson.domain.repository.LessonRepository
import kotlinx.coroutines.flow.Flow

class GetSumDurationUseCase(
    private val lessonRepository: LessonRepository
) {
    operator fun invoke(): Flow<Long> {
        return lessonRepository.getSumDuration()
    }
}