package com.project.managelesson.study.domain.use_case.task_use_case

import com.project.managelesson.study.domain.model.Task
import com.project.managelesson.study.domain.repository.TaskRepository

class GetTaskUseCase(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(taskId: Int): Task? {
        return taskRepository.getTaskById(taskId)
    }
}