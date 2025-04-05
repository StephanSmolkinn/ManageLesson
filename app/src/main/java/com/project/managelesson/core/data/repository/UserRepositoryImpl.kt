package com.project.managelesson.core.data.repository

import com.project.managelesson.auth.data.mappers.toDataUser
import com.project.managelesson.auth.data.mappers.toDomainUser
import com.project.managelesson.auth.domain.model.User
import com.project.managelesson.core.data.data_source.UserDao
import com.project.managelesson.core.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
): UserRepository {
    override suspend fun upsertUser(user: User) {
        userDao.upsertUser(user.toDataUser())
    }

    override suspend fun getUser(): User? {
        return userDao.getUser()?.toDomainUser()
    }
}