package com.project.managelesson.domain.use_case.task_use_case

import com.project.managelesson.domain.model.Task
import com.project.managelesson.domain.repository.TaskRepository

class GetTaskUseCase(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(taskId: Int): Task? {
        return taskRepository.getTaskById(taskId)
    }
}