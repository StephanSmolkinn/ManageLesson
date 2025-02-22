package com.project.managelesson.domain.repository

import com.project.managelesson.domain.model.User

interface UserRepository {
    suspend fun upsertUser(user: User)

    suspend fun getUser(): User?
}