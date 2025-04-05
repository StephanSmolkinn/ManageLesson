package com.project.managelesson.study.domain.use_case.lesson_use_case

import com.project.managelesson.study.domain.model.Lesson
import com.project.managelesson.study.domain.repository.LessonRepository

class InsertLessonUseCase(
    private val lessonRepository: LessonRepository
) {
    suspend operator fun invoke(lesson: Lesson) {
        return lessonRepository.insertLesson(lesson)
    }
}