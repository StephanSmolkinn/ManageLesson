package com.project.managelesson.study.data.data_source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.project.managelesson.study.data.model.LessonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LessonDao {
    @Insert
    suspend fun insertLesson(lessonEntity: LessonEntity)

    @Delete
    suspend fun deleteLesson(lessonEntity: LessonEntity)

    @Query("SELECT * FROM lesson WHERE subjectId = :subjectId")
    fun getRecentLessonBySubjectId(subjectId: Int): Flow<List<LessonEntity>>

    @Query("SELECT * FROM lesson")
    fun getAllLesson(): Flow<List<LessonEntity>>

    @Query("SELECT SUM(duration) FROM lesson")
    fun getSumDuration(): Flow<Long>

    @Query("SELECT SUM(duration) FROM lesson WHERE subjectId = :subjectId")
    fun getSumDurationBySubjectId(subjectId: Int): Flow<Long>

    @Query("DELETE FROM lesson WHERE subjectId = :subjectId")
    suspend fun deleteLessonBySubjectId(subjectId: Int)
}