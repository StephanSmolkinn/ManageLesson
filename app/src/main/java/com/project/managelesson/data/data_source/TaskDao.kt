package com.project.managelesson.data.data_source

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.project.managelesson.domain.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Upsert
    suspend fun upsertTask(task: Task)

    @Query("DELETE FROM task WHERE id = :taskId")
    suspend fun deleteTask(taskId: Int)

    @Query("DELETE FROM task WHERE subjectId = :subjectId")
    suspend fun deleteTaskBySubjectId(subjectId: Int)

    @Query("SELECT * FROM task WHERE id = :taskId")
    suspend fun getTaskById(taskId: Int): Task?

    @Query("SELECT * FROM task WHERE subjectId = :subjectId")
    fun getTaskBySubjectId(subjectId: Int): Flow<List<Task>>

    @Query("SELECT * FROM task")
    fun getAllTask(): Flow<List<Task>>
}