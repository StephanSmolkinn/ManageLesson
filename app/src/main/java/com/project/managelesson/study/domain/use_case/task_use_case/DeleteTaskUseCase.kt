package com.project.managelesson.study.domain.use_case.task_use_case

import com.project.managelesson.study.domain.repository.TaskRepository

class DeleteTaskUseCase(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(taskId: Int) {
        taskRepository.deleteTask(taskId)
    }
}