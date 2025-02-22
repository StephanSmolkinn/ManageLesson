package com.project.managelesson.data.repository

import com.project.managelesson.data.data_source.local.TaskDao
import com.project.managelesson.domain.model.Task
import com.project.managelesson.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
): TaskRepository {
    override suspend fun upsertTask(task: Task) {
        taskDao.upsertTask(task)
    }

    override suspend fun deleteTask(taskId: Int) {
        taskDao.deleteTask(taskId)
    }

    override suspend fun getTaskById(taskId: Int): Task? {
        return taskDao.getTaskById(taskId)
    }

    override fun getUpcomingTaskForSubject(subjectId: Int): Flow<List<Task>> {
        return taskDao.getTaskBySubjectId(subjectId)
    }

    override fun getCompletedTaskForSubject(subjectId: Int): Flow<List<Task>> {
        return taskDao.getTaskBySubjectId(subjectId)
    }

    override fun getAllUpcomingTask(): Flow<List<Task>> {
        return taskDao.getAllTask()
    }
}