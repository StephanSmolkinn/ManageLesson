package com.project.managelesson.study.domain.use_case.lesson_use_case

import com.project.managelesson.study.domain.repository.LessonRepository
import kotlinx.coroutines.flow.Flow

class GetSumDurationBySubjectUseCase(
    private val lessonRepository: LessonRepository
) {
    operator fun invoke(subjectId: Int): Flow<Long> {
        return lessonRepository.getSumDurationBySubjectId(subjectId)
    }
}