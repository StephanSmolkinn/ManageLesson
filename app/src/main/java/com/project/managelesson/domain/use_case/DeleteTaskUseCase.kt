package com.project.managelesson.domain.use_case

import com.project.managelesson.domain.repository.TaskRepository

class DeleteTaskUseCase(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(taskId: Int) {
        taskRepository.deleteTask(taskId)
    }
}