package com.project.managelesson.study.domain.use_case.task_use_case

import com.project.managelesson.study.data.model.InvalidTaskException
import com.project.managelesson.study.domain.model.Task
import com.project.managelesson.study.domain.repository.TaskRepository

class UpsertTaskUseCase(
    private val taskRepository: TaskRepository
) {
    @Throws(InvalidTaskException::class)
    suspend operator fun invoke(task: Task) {
        val error: String? = when {
            task.title.isEmpty() -> "Title is empty"
            task.title.length < 3 -> "Few letters. Write more letters"
            task.title.length > 15 -> "Many letters. Write less letters"
            else -> null
        }
        error?.let {
            throw InvalidTaskException(it)
        }
        taskRepository.upsertTask(task)
    }
}