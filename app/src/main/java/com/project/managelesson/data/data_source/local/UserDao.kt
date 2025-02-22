package com.project.managelesson.data.data_source.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.project.managelesson.domain.model.User

@Dao
interface UserDao {
    @Upsert
    suspend fun upsertUser(user: User)

    @Query("SELECT * FROM user WHERE id = 1")
    suspend fun getUser(): User?
}