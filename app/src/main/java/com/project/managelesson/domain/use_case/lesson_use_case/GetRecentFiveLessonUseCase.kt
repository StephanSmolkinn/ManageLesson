package com.project.managelesson.domain.use_case.lesson_use_case

import com.project.managelesson.domain.model.Lesson
import com.project.managelesson.domain.repository.LessonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take

class GetRecentFiveLessonUseCase(
    private val lessonRepository: LessonRepository
) {
    operator fun invoke(): Flow<List<Lesson>> {
        return lessonRepository.getRecentFiveLesson()
            .map {
                it.sortedByDescending { lesson -> lesson.date }
            }.take(count = 5)
    }
}