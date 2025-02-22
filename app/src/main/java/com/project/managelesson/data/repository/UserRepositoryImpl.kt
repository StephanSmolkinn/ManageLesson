package com.project.managelesson.data.repository

import com.project.managelesson.data.data_source.local.UserDao
import com.project.managelesson.domain.model.User
import com.project.managelesson.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
): UserRepository {
    override suspend fun upsertUser(user: User) {
        userDao.upsertUser(user)
    }

    override suspend fun getUser(): User? {
        return userDao.getUser()
    }
}