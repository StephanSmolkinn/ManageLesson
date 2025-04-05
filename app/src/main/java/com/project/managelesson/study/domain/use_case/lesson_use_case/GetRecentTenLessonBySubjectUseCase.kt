package com.project.managelesson.study.domain.use_case.lesson_use_case

import com.project.managelesson.study.domain.model.Lesson
import com.project.managelesson.study.domain.repository.LessonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take

class GetRecentTenLessonBySubjectUseCase(
    private val lessonRepository: LessonRepository
) {
    operator fun invoke(subjectId: Int): Flow<List<Lesson>> {
        return lessonRepository.getRecentTenLessonBySubject(subjectId)
            .map {
                it.sortedByDescending { lesson -> lesson.date }
            }.take(10)
    }
}