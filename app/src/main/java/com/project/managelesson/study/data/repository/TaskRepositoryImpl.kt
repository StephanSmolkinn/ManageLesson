package com.project.managelesson.study.data.repository

import com.project.managelesson.study.data.data_source.local.TaskDao
import com.project.managelesson.study.data.mappers.toTaskDomain
import com.project.managelesson.study.data.mappers.toTaskEntity
import com.project.managelesson.study.domain.model.Task
import com.project.managelesson.study.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
): TaskRepository {
    override suspend fun upsertTask(task: Task) {
        taskDao.upsertTask(task.toTaskEntity())
    }

    override suspend fun deleteTask(taskId: Int) {
        taskDao.deleteTask(taskId)
    }

    override suspend fun getTaskById(taskId: Int): Task? {
        return taskDao.getTaskById(taskId)?.toTaskDomain()
    }

    override fun getUpcomingTaskForSubject(subjectId: Int): Flow<List<Task>> {
        return taskDao.getTaskBySubjectId(subjectId).map {
            it.map {
                it.toTaskDomain()
            }
        }
    }

    override fun getCompletedTaskForSubject(subjectId: Int): Flow<List<Task>> {
        return taskDao.getTaskBySubjectId(subjectId).map {
            it.map {
                it.toTaskDomain()
            }
        }
    }

    override fun getAllUpcomingTask(): Flow<List<Task>> {
        return taskDao.getAllTask().map {
            it.map {
                it.toTaskDomain()
            }
        }
    }
}