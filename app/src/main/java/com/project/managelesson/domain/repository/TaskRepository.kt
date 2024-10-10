package com.project.managelesson.domain.repository

import com.project.managelesson.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun upsertTask(task: Task)

    suspend fun deleteTask(taskId: Int)

    suspend fun getTaskById(taskId: Int): Task?

    fun getUpcomingTaskForSubject(subjectId: Int): Flow<List<Task>>

    fun getCompletedTaskForSubject(subjectId: Int): Flow<List<Task>>

    fun getAllUpcomingTask(): Flow<List<Task>>
}