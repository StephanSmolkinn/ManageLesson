package com.project.managelesson.core.data.data_model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "user"
)
data class UserEntity(
    @PrimaryKey
    val id: Int? = null,
    val fullName: String,
    val email: String,
    val authToken: String? = null
)