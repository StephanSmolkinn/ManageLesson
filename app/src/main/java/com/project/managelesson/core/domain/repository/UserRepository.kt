package com.project.managelesson.core.domain.repository

import com.project.managelesson.auth.domain.model.User


interface UserRepository {
    suspend fun upsertUser(user: User)

    suspend fun getUser(): User?
}