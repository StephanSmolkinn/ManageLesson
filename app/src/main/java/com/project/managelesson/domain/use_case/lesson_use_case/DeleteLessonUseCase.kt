package com.project.managelesson.domain.use_case.lesson_use_case

import com.project.managelesson.domain.model.Lesson
import com.project.managelesson.domain.repository.LessonRepository

class DeleteLessonUseCase(
    private val lessonRepository: LessonRepository
) {
    suspend operator fun invoke(lesson: Lesson) {
        lessonRepository.deleteLesson(lesson)
    }
}