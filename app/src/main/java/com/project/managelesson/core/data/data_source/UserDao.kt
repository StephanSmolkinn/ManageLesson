package com.project.managelesson.core.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.project.managelesson.core.data.data_model.UserEntity

@Dao
interface UserDao {
    @Upsert
    suspend fun upsertUser(user: UserEntity)

    @Query("SELECT * FROM user WHERE id = 1")
    suspend fun getUser(): UserEntity?

    @Delete
    suspend fun deleteUser(user: UserEntity)
}