package com.project.managelesson.domain.use_case

import com.project.managelesson.domain.model.Task
import com.project.managelesson.domain.repository.TaskRepository
import com.project.managelesson.domain.util.sortTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetUpcomingTaskBySubjectUseCase(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(subjectId: Int): Flow<List<Task>> {
        return taskRepository.getUpcomingTaskForSubject(subjectId)
            .map { tasks -> tasks.filter { it.isCompleted.not() } }
            .map { tasks -> sortTask(tasks) }
    }
}