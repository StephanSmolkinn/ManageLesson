package com.project.managelesson.study.data.data_source.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.project.managelesson.study.data.model.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Upsert
    suspend fun upsertTask(task: TaskEntity)

    @Query("DELETE FROM task WHERE id = :taskId")
    suspend fun deleteTask(taskId: Int)

    @Query("DELETE FROM task WHERE subjectId = :subjectId")
    suspend fun deleteTaskBySubjectId(subjectId: Int)

    @Query("SELECT * FROM task WHERE id = :taskId")
    suspend fun getTaskById(taskId: Int): TaskEntity?

    @Query("SELECT * FROM task WHERE subjectId = :subjectId")
    fun getTaskBySubjectId(subjectId: Int): Flow<List<TaskEntity>>

    @Query("SELECT * FROM task")
    fun getAllTask(): Flow<List<TaskEntity>>
}