package com.project.managelesson.domain.use_case

import com.project.managelesson.domain.model.Lesson
import com.project.managelesson.domain.repository.LessonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take

class GetRecentTenLessonBySubjectUseCase(
    private val lessonRepository: LessonRepository
) {
    operator fun invoke(subjectId: Int): Flow<List<Lesson>> {
        return lessonRepository.getRecentTenLessonBySubject(subjectId).take(10)
    }
}