package com.project.managelesson.study.domain.use_case.task_use_case

import com.project.managelesson.core.domain.util.sortTask
import com.project.managelesson.study.domain.model.Task
import com.project.managelesson.study.domain.repository.TaskRepository
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