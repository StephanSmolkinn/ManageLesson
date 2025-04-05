package com.project.managelesson.study.data.data_source.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.project.managelesson.study.data.model.SubjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {
    @Upsert
    suspend fun upsertSubject(subject: SubjectEntity)

    @Query("SELECT COUNT(*) FROM subject")
    fun getSubjectCount(): Flow<Int>

    @Query("SELECT SUM(goalHours) FROM subject")
    fun getSubjectHours(): Flow<Double>

    @Query("SELECT * FROM subject WHERE id = :subjectId")
    suspend fun getSubjectById(subjectId: Int): SubjectEntity?

    @Query("DELETE FROM subject WHERE id = :subjectId")
    suspend fun deleteSubject(subjectId: Int)

    @Query("SELECT * FROM subject")
    fun getAllSubject(): Flow<List<SubjectEntity>>
}