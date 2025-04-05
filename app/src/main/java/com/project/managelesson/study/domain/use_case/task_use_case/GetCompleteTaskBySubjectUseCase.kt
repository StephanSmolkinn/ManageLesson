package com.project.managelesson.study.domain.use_case.task_use_case

import com.project.managelesson.core.domain.util.sortTask
import com.project.managelesson.study.domain.model.Task
import com.project.managelesson.study.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCompleteTaskBySubjectUseCase(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(subjectId: Int): Flow<List<Task>> {
        return taskRepository.getCompletedTaskForSubject(subjectId)
            .map { tasks -> tasks.filter { it.isCompleted } }
            .map { tasks -> sortTask(tasks) }
    }
}