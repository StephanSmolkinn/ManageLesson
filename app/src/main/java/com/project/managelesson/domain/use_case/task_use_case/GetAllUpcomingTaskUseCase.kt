package com.project.managelesson.domain.use_case.task_use_case

import com.project.managelesson.domain.model.Task
import com.project.managelesson.domain.repository.TaskRepository
import com.project.managelesson.domain.util.sortTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllUpcomingTaskUseCase(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(): Flow<List<Task>> {
        return taskRepository.getAllUpcomingTask()
            .map { tasks -> tasks.filter { it.isCompleted.not() } }
            .map { tasks -> sortTask(tasks) }
    }
}