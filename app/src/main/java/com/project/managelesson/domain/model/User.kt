package com.project.managelesson.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    val id: Int? = null,
    val fullName: String,
    val email: String,
    val authToken: String? = null
)